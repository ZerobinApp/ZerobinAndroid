package com.shop.zerobin.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentMyPageStampBinding
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.common.ImageViewPagerActivity
import com.shop.zerobin.ui.home.shop.ShopDetailFragmentDirections
import com.shop.zerobin.ui.review.DialogArticleTap
import com.shop.zerobin.ui.review.adapter.ReviewAdapter
import org.koin.android.viewmodel.ext.android.viewModel


class MyPageStampFragment :
    BaseBindingFragment<FragmentMyPageStampBinding>(R.layout.fragment_my_page_stamp) {

    private val myPageViewModel: MyPageViewModel by viewModel()

    private val myPageStampAdapter by lazy {
        ReviewAdapter().apply {
            onImageClick = { reviewIndex, position ->
                val intent = Intent(requireContext(), ImageViewPagerActivity::class.java)
                val imageList: Array<String> =
                    myPageViewModel.myUserStamp.value?.get(reviewIndex)?.imageList?.toTypedArray()
                        ?: emptyArray()
                intent.putExtra(ImageViewPagerActivity.EXTRA_IMAGE_LIST, imageList)
                intent.putExtra(ImageViewPagerActivity.EXTRA_IMAGE_INDEX, position)
                startActivity(intent)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reviewVM = myPageViewModel

        setReviewAdapter()
        observeLiveData()
        requestShopList()
        setOnClickListeners()
    }

    private fun setReviewAdapter() {
        binding.myStampRecyclerView.adapter = myPageStampAdapter
    }

    private fun observeLiveData() {
        myPageViewModel.myUserStamp.observe(viewLifecycleOwner) {
            myPageStampAdapter.setItem(it)
            if (it.isEmpty()) {
                binding.stampEmptyImageView.isVisible = true
                binding.stampEmptyTextView.isVisible = true
            }
        }

        myPageViewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { it ->
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        myPageViewModel.isError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setOnClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        myPageStampAdapter.setOnItemClickListener(object : ReviewAdapter.OnItemClickListener {
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
                                    myPageViewModel.requestReviewDelete(
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

    private fun requestShopList() {
        myPageViewModel.requestMyPageStamp()
    }
}