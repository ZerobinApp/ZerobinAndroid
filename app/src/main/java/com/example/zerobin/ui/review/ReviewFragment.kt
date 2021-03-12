package com.example.zerobin.ui.review

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.zerobin.R
import com.example.zerobin.databinding.FragmentReviewBinding
import com.example.zerobin.ui.common.BaseBindingFragment
import com.example.zerobin.ui.review.adapter.ReviewAdapter


class ReviewFragment : BaseBindingFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    private val reviewViewModel: ReviewViewModel by viewModels()

    private val reviewAdapter by lazy { ReviewAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reviewVM = reviewViewModel

        setReviewAdapter()
        observeLiveData()
        requestReviewList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
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