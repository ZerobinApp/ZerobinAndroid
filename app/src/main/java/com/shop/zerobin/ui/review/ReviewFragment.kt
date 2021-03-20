package com.shop.zerobin.ui.review

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentReviewBinding
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
        requestReviewList()
        setListeners()
    }

    private fun setListeners(){
        binding.btnFilter.setOnClickListener {
            //리뷰 필터 액티비티로 넘어가기
            startActivity(Intent(context,ReviewFilterActivity::class.java))
        }
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

    private fun requestReviewList() {
        reviewViewModel.requestReviewList()
    }

}