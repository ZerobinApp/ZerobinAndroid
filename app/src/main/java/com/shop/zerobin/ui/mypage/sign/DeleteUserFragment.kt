package com.shop.zerobin.ui.mypage.sign

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentDeleteUserBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import org.koin.android.viewmodel.ext.android.viewModel

class DeleteUserFragment :
    BaseBindingFragment<FragmentDeleteUserBinding>(R.layout.fragment_delete_user) {

    private val viewModel: DeleteUserViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        observeLiveData()
        setListeners()
    }

    private fun observeLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        viewModel.isError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.deleteApi.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { success ->
                if (success) {
                    deleteFirebase()
                }
            }
        }
    }

    private fun setListeners() {
        binding.btnDelete.setOnClickListener {
            reAuthenticateFirebase()
        }

        binding.resetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_sign_up_to_navigation_reset_password)
        }
    }

    private fun reAuthenticateFirebase() {
        val email = viewModel.email.value ?: return
        val password = viewModel.inputPassword.value ?: return
        val credential = EmailAuthProvider.getCredential(email, password)
        Firebase.auth.currentUser?.reauthenticate(credential)?.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "reAuthenticate > Success > ${it.result}")
                viewModel.deleteUser()
            } else {
                Log.d(TAG, "reAuthenticate > Fail > ${it.exception}")
                Toast.makeText(requireContext(),
                    "인증실패\n${it.exception?.message}",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteFirebase() {
        Firebase.auth.currentUser?.delete()
        Toast.makeText(requireContext(), "회원탈퇴 되었습니다.", Toast.LENGTH_SHORT).show()
        hideLoading()
        findNavController().popBackStack()
    }

    companion object {
        private val TAG: String = DeleteUserFragment::class.java.simpleName
    }
}