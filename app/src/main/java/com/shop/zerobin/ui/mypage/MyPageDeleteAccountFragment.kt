package com.shop.zerobin.ui.mypage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentMyPageBinding
import com.shop.zerobin.databinding.FragmentMyPageDeleteAccountBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MyPageDeleteAccountFragment : BaseBindingFragment<FragmentMyPageDeleteAccountBinding>(R.layout.fragment_my_page_delete_account){


    private val myPageViewModel: MyPageViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = myPageViewModel

        setListeners()
        observeLiveData()
    }


    private fun setListeners(){
        binding.btnUserDelete.setOnClickListener {
            myPageViewModel.requestDeleteAccount()

        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    fun observeLiveData(){
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

}