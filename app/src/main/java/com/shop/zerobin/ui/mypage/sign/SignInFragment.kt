package com.shop.zerobin.ui.mypage.sign

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shop.zerobin.R
import com.shop.zerobin.databinding.FragmentSignInBinding
import com.shop.zerobin.ui.common.BaseBindingFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SignInFragment : BaseBindingFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    private val signInViewModel: SignInViewModel by viewModel()
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = signInViewModel

        setListeners()
        observeLiveData()
    }

    private fun setListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_sign_up)
        }
    }

    private fun observeLiveData() {
        signInViewModel.isLoading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        signInViewModel.isError.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }

        signInViewModel.inputCheckComplete.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                signIn(
                    signInViewModel.inputEmail.value ?: return@observe,
                    signInViewModel.inputPassword.value ?: return@observe
                )
            }
        }

        signInViewModel.signInFinish.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                findNavController().popBackStack()
            }
        }
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        showLoading()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")

                    val user = auth.currentUser
                    signInViewModel.requestSignIn(auth.uid ?: return@addOnCompleteListener)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(), "로그인 실패\n${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                hideLoading()
            }
    }

    companion object {
        private val TAG: String = SignInFragment::class.java.simpleName
    }
}