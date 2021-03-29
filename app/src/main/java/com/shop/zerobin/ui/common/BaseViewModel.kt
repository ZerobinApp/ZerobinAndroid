package com.shop.zerobin.ui.common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    protected val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    protected val _isError = MutableLiveData<Event<String>>()
    val isError: LiveData<Event<String>> = _isError

    protected val _isLogin = MutableLiveData<Boolean>(false)
    val isLogin: LiveData<Boolean> = _isLogin

    protected open fun handleError(tag: String, exception: Exception) {
        Log.e(tag, exception.message ?: "handleError")
        _isLoading.value = Event(false)
        _isError.value = Event(exception.message ?: "Error")
    }

    protected fun handleLoading() {
        _isLoading.value = Event(true)
    }
}