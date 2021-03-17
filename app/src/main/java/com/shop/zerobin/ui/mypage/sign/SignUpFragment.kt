package com.shop.zerobin.ui.mypage.sign

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentSignUpBinding
import com.shop.zerobin.ui.common.BaseBindingFragment

class SignUpFragment : BaseBindingFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    private val signUpViewModel: SignUpViewModel by viewModels()
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = signUpViewModel

        setListeners()
        observeLiveData()
    }

    private fun setListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeLiveData() {
        signUpViewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        signUpViewModel.isError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }

        signUpViewModel.inputCheckComplete.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                createAccount(
                    signUpViewModel.inputEmail.value ?: return@observe,
                    signUpViewModel.inputPassword.value ?: return@observe
                )
            }
        }

        signUpViewModel.signUpFinish.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                findNavController().navigate(R.id.action_navigation_sign_up_to_navigation_my_page)
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        showLoading()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")

                    val user = auth.currentUser
                    signUpViewModel.requestSignUp(auth.uid ?: return@addOnCompleteListener)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        "회원가입 실패\n${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                hideLoading()
            }
    }

    private fun resetPassword(emailAddress: String) {
        auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                }
            }
    }

    companion object {
        private val TAG: String = this::class.java.simpleName
    }
}