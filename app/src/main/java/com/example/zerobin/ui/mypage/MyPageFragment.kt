package com.example.zerobin.ui.mypage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.fragment.app.viewModels
import com.example.zerobin.R
import com.example.zerobin.databinding.FragmentMyPageBinding
import com.example.zerobin.ui.common.BaseBindingFragment

class MyPageFragment : BaseBindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userVM = myPageViewModel

        requestReviewList()
        setListeners()
        observeLiveData()
    }

    private fun requestReviewList() {
        myPageViewModel.requestMyPage()
    }

    private fun setListeners() {
        binding.nextBtnFavoriteReview.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_my_page_to_navigation_my_page_review)
        }

        binding.nextBtnFavoriteShop.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_my_page_to_navigation_my_page_shop)
        }

        binding.nextBtnFavoriteStamp.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_my_page_to_navigation_my_page_stamp)
        }
    }

    private fun observeLiveData() {
        myPageViewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
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
}