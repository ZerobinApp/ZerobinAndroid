package com.shop.zerobin.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentMyPageNickChangeBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import org.koin.android.viewmodel.ext.android.viewModel


class MyPageNickChangeFragment :
    BaseBindingFragment<FragmentMyPageNickChangeBinding>(R.layout.fragment_my_page_nick_change) {

    private val myPageViewModel: MyPageViewModel by viewModel()

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
        myPageViewModel.inputCheckComplete.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                changeNickName(
                    myPageViewModel.inputNickName.value ?: return@observe,
                )
            }
        }
        myPageViewModel.nickNameChangeFinish.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                findNavController().navigate(R.id.action_navigation_nick_change_to_navigation_my_page)
            }
        }
    }

    private fun changeNickName(nickname: String) {
        myPageViewModel.requestNickNameChange(nickname)

    }


}