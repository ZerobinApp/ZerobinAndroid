package com.shop.zerobin.ui.mypage.sign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.zerobin.data.repository.mypage.MyPageRepository
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.ui.common.BaseViewModel
import com.shop.zerobin.ui.common.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignUpViewModel(private val myPageRepository: MyPageRepository) : BaseViewModel() {
    val inputEmail = MutableLiveData("")
    val inputPassword = MutableLiveData("")
    val inputPasswordConfirm = MutableLiveData("")
    val inputNickname = MutableLiveData("")

    private val _inputCheckComplete = MutableLiveData<Event<Unit>>()
    val inputCheckComplete: LiveData<Event<Unit>> = _inputCheckComplete

    private val _signUpFinish = MutableLiveData<Event<Boolean>>()
    val signUpFinish: LiveData<Event<Boolean>> = _signUpFinish

    fun onClickComplete() {
        if (!inputCheck()) return

        createFirebaseAccount()
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

        if (inputPasswordConfirm.value?.isBlank() == true) {
            _isError.value = Event("비밀번호를 한번 더 입력하세요.")
            return false
        }

        if (inputNickname.value?.isBlank() == true) {
            _isError.value = Event("닉네임을 입력하세요.")
            return false
        }

        if (inputPassword.value != inputPasswordConfirm.value) {
            _isError.value = Event("비밀번호가 일치하지 않습니다.")
            return false
        }
        return true
    }

    private fun createFirebaseAccount() {
        _inputCheckComplete.value = Event(Unit)
    }

    fun requestSignUp(password: String) {
        val email = inputEmail.value ?: return
        val nickname = inputNickname.value ?: return

        viewModelScope.launch {
            val response = myPageRepository.signUp(email, password, nickname)
            response.collect { handleResult(it) }
        }
    }

    private fun handleResult(dataResult: DataResult<Unit>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccess()
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            is DataResult.Loading -> handleLoading()
        }
    }

    override fun handleError(tag: String, exception: Exception) {
        super.handleError(tag, exception)
        _signUpFinish.value = Event(false)
    }

    private fun handleSuccess() {
        _isLoading.value = Event(false)
        _isError.value = Event("회원가입 성공")
        _signUpFinish.value = Event(true)
    }

    companion object {
        private val TAG: String = SignUpViewModel::class.java.simpleName
    }
}