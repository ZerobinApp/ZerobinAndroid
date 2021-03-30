package com.shop.zerobin.ui.mypage.sign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shop.zerobin.data.repository.mypage.MyPageRepository
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.ui.common.BaseViewModel
import com.shop.zerobin.ui.common.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DeleteUserViewModel(private val myPageRepository: MyPageRepository) : BaseViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    val inputPassword = MutableLiveData("")

    private val _deleteApi = MutableLiveData<Event<Boolean>>()
    val deleteApi: LiveData<Event<Boolean>> = _deleteApi

    init {
        _email.value = Firebase.auth.currentUser?.email
    }

    fun deleteUser() {
        viewModelScope.launch {
            val response = myPageRepository.deleteUser()
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
        _deleteApi.value = Event(false)
    }

    private fun handleSuccess() {
        _deleteApi.value = Event(true)
    }

    companion object {
        private val TAG: String = DeleteUserViewModel::class.java.simpleName
    }
}