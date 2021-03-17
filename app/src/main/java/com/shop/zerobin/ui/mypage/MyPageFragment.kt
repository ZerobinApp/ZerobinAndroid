package com.shop.zerobin.ui.mypage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentMyPageBinding
import com.shop.zerobin.ui.common.BaseBindingFragment

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
        binding.myReviewLayout.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_page_to_navigation_my_page_review)
        }

        binding.myFavoriteShopLayout.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_page_to_navigation_my_page_shop)
        }

        binding.myStampLayout.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_page_to_navigation_my_page_stamp)
        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_page_to_navigation_sign_in)
        }

        binding.btnNickChange.setOnClickListener {
            //mypagenickchangefragment로 이동!
            findNavController().navigate(R.id.action_navigation_my_page_to_navigation_nick_change)
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