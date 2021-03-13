package com.shop.zerobin.ui.mypage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentMyPageStampBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.review.adapter.ReviewAdapter


class MyPageStampFragment : BaseBindingFragment<FragmentMyPageStampBinding>(R.layout.fragment_my_page_stamp) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    private val myPageStampAdapter by lazy { ReviewAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reviewVM = myPageViewModel

        setShopAdapter()
        observeLiveData()
        requestShopList()
    }

    private fun setShopAdapter() {
        binding.myPageStampRecyclerView.adapter = myPageStampAdapter
    }

    private fun observeLiveData() {
        myPageViewModel.myUserStamp.observe(viewLifecycleOwner) {
            myPageStampAdapter.setItem(it)
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

    private fun requestShopList() {
        myPageViewModel.requestMyPageStamp()
    }
}