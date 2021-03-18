package com.shop.zerobin.ui.mypage

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentMyPageReviewBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.review.adapter.ReviewAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class MyPageReviewFragment :
    BaseBindingFragment<FragmentMyPageReviewBinding>(R.layout.fragment_my_page_review) {

    private val myPageReviewViewModel: MyPageViewModel by viewModel()

    private val reviewAdapter by lazy { ReviewAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reviewVM = myPageReviewViewModel

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
        binding.myPageReviewRecyclerView.adapter = reviewAdapter
    }

    private fun observeLiveData() {
        myPageReviewViewModel.myUserReview.observe(viewLifecycleOwner) {
            reviewAdapter.setItem(it)
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

    private fun requestReviewList() {
        myPageReviewViewModel.requestMyPageReview()
    }

}