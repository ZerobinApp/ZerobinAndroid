package com.example.zerobin.ui.mypage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
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
            setFragment(MyPageReviewFragment())
        }

        binding.nextBtnFavoriteShop.setOnClickListener {
            setFragment(MyPageShopFragment())
        }

        binding.nextBtnFavoriteStamp.setOnClickListener {
            setFragment(MyPageStampFragment())
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

    private fun setFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}