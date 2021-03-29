package com.shop.zerobin.ui.mypage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentMyPageBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.mypage.sign.SignInFragmentDirections
import org.koin.android.viewmodel.ext.android.viewModel

class MyPageFragment : BaseBindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val myPageViewModel: MyPageViewModel by viewModel()
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userVM = myPageViewModel

        requestMyPage()
        setListeners()
        observeLiveData()
    }

    private fun requestMyPage() {
        myPageViewModel.requestMyPage()
    }

    private fun setListeners() {
        binding.myReviewLayout.setOnClickListener {
            if (myPageViewModel.isLogin.value == true) {
                findNavController().navigate(R.id.action_navigation_my_page_to_navigation_my_page_review)
            }
        }

        binding.myFavoriteShopLayout.setOnClickListener {
            if (myPageViewModel.isLogin.value == true) {
                findNavController().navigate(R.id.action_navigation_my_page_to_navigation_my_page_shop)
            }
        }

        binding.myStampLayout.setOnClickListener {
            if (myPageViewModel.isLogin.value == true) {
                findNavController().navigate(R.id.action_navigation_my_page_to_navigation_my_page_stamp)
            }
        }

        binding.btnLogin.setOnClickListener {
            if (myPageViewModel.isLogin.value == true) {
                Firebase.auth.signOut()
                myPageViewModel.requestLogout()
                myPageViewModel.requestMyPage()
            } else {
                val action = SignInFragmentDirections.actionGlobalNavigationSignIn()
                findNavController().navigate(action)
            }
        }

        binding.btnNickChange.setOnClickListener {
            if (myPageViewModel.isLogin.value == true) {
                findNavController().navigate(R.id.action_navigation_my_page_to_navigation_nick_change)
            }
        }

        binding.btnDeleteAccount.setOnClickListener {
            // todo : 회원탈퇴 (서버 api 통신 -> firebase 탈퇴)
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

    companion object {
        private val TAG : String = MyPageFragment::class.java.simpleName
    }
}