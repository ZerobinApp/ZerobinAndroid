package com.shop.zerobin.ui.mypage.sign

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shop.zerobin.data.repository.mypage.MyPageRepository
import com.shop.zerobin.ui.common.BaseViewModel
import com.shop.zerobin.ui.common.Event

class ResetPasswordViewModel(private val myPageRepository: MyPageRepository): BaseViewModel() {

    val inputEmail = MutableLiveData("")

    private val _inputCheckComplete = MutableLiveData<Event<String>>()
    val inputCheckComplete: LiveData<Event<String>> = _inputCheckComplete

    fun onClickComplete() {
        val email = inputEmail.value ?: ""
        if (email.isBlank()) {
            _isError.value = Event("이메일을 입력해주시고 클릭해주세요.")
            return
        }
        val patterns = Patterns.EMAIL_ADDRESS
        if (!patterns.matcher(email).matches()) {
            _isError.value = Event("이메일 형식으로 입력해주세요.")
            return
        }

        _inputCheckComplete.value = Event(email)
    }
}