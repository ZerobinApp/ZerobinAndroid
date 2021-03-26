package com.shop.zerobin.ui.review

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentReviewBinding
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.review.adapter.ReviewAdapter
import org.koin.android.viewmodel.ext.android.viewModel


class ReviewFragment : BaseBindingFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    private val reviewViewModel: ReviewViewModel by viewModel()

    private val reviewAdapter by lazy { ReviewAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reviewVM = reviewViewModel

        setReviewAdapter()
        observeLiveData()
        if (arguments?.getIntegerArrayList("hashtag") == null) {
            val hashtagList = emptyList<Int>()
            requestReviewList(hashtagList)
        } else {
            requestReviewList(arguments?.getIntegerArrayList("hashtag")!!)

        }
        setListeners()
    }

    private fun setListeners() {
        binding.btnFilter.setOnClickListener {
            //리뷰 필터 액티비티로 넘어가기
            findNavController().navigate(R.id.action_navigation_review_to_navigation_review_filter)
        }

        reviewAdapter.setOnItemClickListener(object : ReviewAdapter.OnItemClickListener {
            //상점이름을 클릭시 상점 상세페이지로 넘어가기 ...
            override fun onItemClick(review: Review) {
                Toast.makeText(context, review.name, Toast.LENGTH_SHORT).show()
            }

            override fun onMenuClick(review: Review) {
                if (review.owner) {
                    DialogArticleTap().apply {
                        viewType = DialogArticleTap.ViewType.MINE
                        onClick = { clickType ->
                            when (clickType) {
                                DialogArticleTap.ClickType.EDIT -> Toast.makeText(
                                    requireContext(),
                                    "리뷰수정",
                                    Toast.LENGTH_SHORT
                                ).show()
                                DialogArticleTap.ClickType.DELETE -> reviewViewModel.requestReviewDelete(
                                    review.reviewIndex,
                                    review.shopIndex
                                )
                                else -> Toast.makeText(
                                    requireContext(),
                                    "잘못된 접근",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }.show(childFragmentManager, "dialog.tag")
                } else {
                    DialogArticleTap().apply {
                        viewType = DialogArticleTap.ViewType.OTHER
                        onClick = { clickType ->
                            when (clickType) {
                                DialogArticleTap.ClickType.REPORT -> reviewViewModel.requestReviewReport(
                                    review.reviewIndex
                                )
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

    private fun setReviewAdapter() {
        binding.reviewRecyclerView.adapter = reviewAdapter
    }

    private fun observeLiveData() {
        reviewViewModel.reviewList.observe(viewLifecycleOwner) {

            reviewAdapter.setItem(it)
        }

        reviewViewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { it ->
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        reviewViewModel.isError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun requestReviewList(hashtagList: List<Int>) {
        reviewViewModel.requestReviewList(hashtagList)
    }

}
