package com.shop.zerobin.ui.mypage.sign

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.zerobin.data.repository.mypage.MyPageRepository
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.ui.common.BaseViewModel
import com.shop.zerobin.ui.common.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignInViewModel(private val myPageRepository: MyPageRepository) : BaseViewModel() {
    val inputEmail = MutableLiveData("")
    val inputPassword = MutableLiveData("")

    private val _inputCheckComplete = MutableLiveData<Event<Unit>>()
    val inputCheckComplete: LiveData<Event<Unit>> = _inputCheckComplete

    private val _signInFinish = MutableLiveData<Event<Unit>>()
    val signInFinish: LiveData<Event<Unit>> = _signInFinish

    fun onClickLogin() {
        if (!inputCheck()) return

        _inputCheckComplete.value = Event(Unit)
    }

    private fun inputCheck(): Boolean {
        if (inputEmail.value?.isBlank() == true) {
            _isError.value = Event("이메일을 입력하세요.")
            return false
        }

        if (inputPassword.value?.isBlank() == true) {
            _isError.value = Event("비밀번호를 입력하세요.")
            return false
        }

        return true
    }

    fun requestSignIn(password: String) {
        val email = inputEmail.value ?: return

        viewModelScope.launch {
            val response = myPageRepository.signIn(email, password)
            response.collect { handleResult(it) }
        }
    }

    private fun handleResult(dataResult: DataResult<String>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccess(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            is DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSuccess(jwt: String) {
        Log.d(TAG, "로그인 성공 $jwt")
        _signInFinish.value = Event(Unit)
    }

    companion object {
        private val TAG: String = this::class.java.simpleName
    }
}