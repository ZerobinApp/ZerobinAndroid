package com.shop.zerobin.ui.mypage.sign

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentResetPasswordBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import com.shop.zerobin.ui.common.CustomDialog
import org.koin.android.viewmodel.ext.android.viewModel

class ResetPasswordFragment: BaseBindingFragment<FragmentResetPasswordBinding>(R.layout.fragment_reset_password) {

    private val resetPasswordViewModel : ResetPasswordViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = resetPasswordViewModel

        setListeners()
        observeLiveData()
    }

    private fun setListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeLiveData() {
        resetPasswordViewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        resetPasswordViewModel.isError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }

        resetPasswordViewModel.inputCheckComplete.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { email ->
                resetPassword(email)
            }
        }
    }

    private fun resetPassword(emailAddress: String) {
        Firebase.auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    CustomDialog(requireContext(), R.style.CustomDialog).apply {
                        description = "입력하신 이메일로 비밀번호 재설정 메일을 보냈습니다."
                    }.show()
                } else {
                    CustomDialog(requireContext(), R.style.CustomDialog).apply {
                        description = "회원가입이 되지 않은 이메일입니다.\n다시 확인해주세요."
                    }.show()
                }
            }
    }
}