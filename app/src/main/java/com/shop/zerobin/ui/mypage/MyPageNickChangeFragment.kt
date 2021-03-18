package com.shop.zerobin.ui.mypage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentMyPageNickChangeBinding
import com.shop.zerobin.ui.common.BaseBindingFragment


class MyPageNickChangeFragment :
    BaseBindingFragment<FragmentMyPageNickChangeBinding>(R.layout.fragment_my_page_nick_change) {

    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = myPageViewModel

        setListeners()
        observeLiveData()
    }

    private fun setListeners() {
        binding.nickNameChangeBackButton.setOnClickListener {
            findNavController().popBackStack()
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

        myPageViewModel.inputCheckComplete.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                changeNickName(
                    myPageViewModel.inputNickName.value ?: return@observe,
                )
            }
        }

        myPageViewModel.nickNameChangeFinish.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                findNavController().popBackStack()
            }
        }
    }

    private fun changeNickName(nickname: String) {
        myPageViewModel.requestNickNameChange(nickname)
    }
}