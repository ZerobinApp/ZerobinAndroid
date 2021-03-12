package com.example.zerobin.ui.common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *  BaseViewModel.kt
 *
 *  Created by Donghyun An on 2021/03/11
 *  Copyright © 2020 Shinhan Bank. All rights reserved.
 */

open class BaseViewModel : ViewModel() {
    protected val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    private val _isError = MutableLiveData<Event<String>>()
    val isError: LiveData<Event<String>> = _isError

    protected fun handleError(tag: String, exception: Exception) {
        Log.e(tag, exception.message ?: "handleError")
        _isLoading.value = Event(false)
        _isError.value = Event(exception.message ?: "Error")
    }

    protected fun handleLoading() {
        _isLoading.value = Event(true)
    }
}