package com.shop.zerobin.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentMyPageReviewBinding
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.common.ImageViewPagerActivity
import com.shop.zerobin.ui.home.shop.ShopDetailFragmentDirections
import com.shop.zerobin.ui.review.DialogArticleTap
import com.shop.zerobin.ui.review.adapter.ReviewAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class MyPageReviewFragment :
    BaseBindingFragment<FragmentMyPageReviewBinding>(R.layout.fragment_my_page_review) {

    private val myPageReviewViewModel: MyPageViewModel by viewModel()

    private val reviewAdapter by lazy {
        ReviewAdapter().apply {
            viewType = ReviewAdapter.ViewType.MINE
            onImageClick = { reviewIndex, position ->
                val intent = Intent(requireContext(), ImageViewPagerActivity::class.java)
                val imageList: Array<String> =
                    myPageReviewViewModel.myUserReview.value?.get(reviewIndex)?.imageList?.toTypedArray()
                        ?: emptyArray()
                intent.putExtra(ImageViewPagerActivity.EXTRA_IMAGE_LIST, imageList)
                intent.putExtra(ImageViewPagerActivity.EXTRA_IMAGE_INDEX, position)
                startActivity(intent)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reviewVM = myPageReviewViewModel

        requestReviewList()
        setReviewAdapter()
        observeLiveData()
        setOnClickListener()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setReviewAdapter() {
        binding.myPageReviewRecyclerView.adapter = reviewAdapter
    }

    private fun observeLiveData() {
        myPageReviewViewModel.myUserReview.observe(viewLifecycleOwner) {
            reviewAdapter.setItem(it)
            if (it.isEmpty()) {
                binding.reviewEmptyImageView.isVisible = true
                binding.reviewEmptyTextView.isVisible = true
            }
        }

        myPageReviewViewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { it ->
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        myPageReviewViewModel.isError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        reviewAdapter.setOnItemClickListener(object : ReviewAdapter.OnItemClickListener {
            override fun onItemClick(review: Review) {
                val action =
                    ShopDetailFragmentDirections.actionGlobalNavigationShopDetail(review.shopIndex)
                findNavController().navigate(action)
            }

            override fun onMenuClick(review: Review) {
                if (review.owner) {
                    DialogArticleTap().apply {
                        viewType = DialogArticleTap.ViewType.MINE
                        onClick = { clickType ->
                            when (clickType) {
                                DialogArticleTap.ClickType.EDIT -> {
                                    val list = mutableListOf(review.shopIndex, review.reviewIndex)
                                    val bundle = bundleOf("list" to list)

                                    findNavController()
                                        .navigate(R.id.action_global_navigation_write_review,
                                            bundle)
                                }
                                DialogArticleTap.ClickType.DELETE -> {
                                    myPageReviewViewModel.requestReviewDelete(
                                        review.shopIndex,
                                        review.reviewIndex
                                    )
                                }
                                else -> Toast.makeText(
                                    requireContext(),
                                    "잘못된 접근",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }.show(childFragmentManager, "dialog.tag")
                }
            }
        })
    }

    private fun requestReviewList() {
        myPageReviewViewModel.requestMyPageReview()
    }

}