package com.shop.zerobin.ui.mypage


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.zerobin.data.repository.mypage.MyPageRepository
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.domain.entity.Shop
import com.shop.zerobin.domain.entity.User
import com.shop.zerobin.ui.common.BaseViewModel
import com.shop.zerobin.ui.common.Event
import com.shop.zerobin.ui.home.HomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MyPageViewModel(private val myPageRepository: MyPageRepository) : BaseViewModel() {
    val inputNickName = MutableLiveData("")

    val inputEmail = MutableLiveData("")
    val inputPassword = MutableLiveData("")

    private val _nickNameChangeFinish = MutableLiveData<Event<Unit>>()
    val nickNameChangeFinish: LiveData<Event<Unit>> = _nickNameChangeFinish

    private val _inputCheckComplete = MutableLiveData<Event<Unit>>()
    val inputCheckComplete: LiveData<Event<Unit>> = _inputCheckComplete

    private val _myUser = MutableLiveData<User>()
    val myUser: LiveData<User> = _myUser

    private val _myUserReview = MutableLiveData<List<Review>>()
    val myUserReview: LiveData<List<Review>> = _myUserReview

    private val _myUserShop = MutableLiveData<List<Shop>>()
    val myUserShop: LiveData<List<Shop>> = _myUserShop

    private val _myUserStamp = MutableLiveData<List<Review>>()
    val myUserStamp: LiveData<List<Review>> = _myUserStamp

    fun onClickComplete() {
        if (!inputCheck()) return

        changeNickname()
    }

    private fun inputCheck(): Boolean {
        if (inputNickName.value?.isBlank() == true) {
            _isError.value = Event("닉네임을 입력하세요.")
            return false
        }

        return true
    }

    private fun changeNickname() {
        _inputCheckComplete.value = Event(Unit)
    }

    fun requestMyPage() {
        _isLogin.value = myPageRepository.isLogin()
        viewModelScope.launch {
            val userResponse = myPageRepository.getMyPageUser()
            userResponse.collect { handleResultUser(it) }
        }
    }

    fun requestNickNameChange(nickname: String) {
        viewModelScope.launch {
            val response = myPageRepository.nickNameChange(nickname)
            response.collect { handleResultNickNameChange(it) }
        }
    }

    fun requestDeleteAccount(){
        viewModelScope.launch {
            val response = myPageRepository.deleteAccount()
            response.collect{ handleResultDeleteAccount(it)}
        }
    }

    private fun handleResultNickNameChange(dataResult: DataResult<Unit>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessNickNameChange()
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            is DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSuccessNickNameChange() {
        _isLoading.value = Event(false)
        _isError.value = Event("닉네임 변경 성공")
        _nickNameChangeFinish.value = Event(Unit)
    }

    fun requestMyPageReview() {
        viewModelScope.launch {
            val response = myPageRepository.getMyPageReview()
            Log.d(HomeViewModel.TAG, response.toString())
            response.collect { handleResultReview(it) }
        }
    }

    fun requestMyPageShop() {
        viewModelScope.launch {
            val response = myPageRepository.getMyPageShop()
            Log.d(HomeViewModel.TAG, response.toString())
            response.collect { handleResultShop(it) }
        }
    }

    fun requestMyPageStamp() {
        viewModelScope.launch {
            val response = myPageRepository.getMyPageStamp()
            Log.d(HomeViewModel.TAG, response.toString())
            response.collect { handleResultStamp(it) }
        }
    }

    fun requestLogout() {
        myPageRepository.deleteJWT()
        _isError.value = Event("로그아웃 완료")
    }

    private fun handleResultReview(dataResult: DataResult<List<Review>>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessReview(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleResultUser(dataResult: DataResult<User>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessUser(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleResultDeleteAccount(dataResult: DataResult<Unit>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessDeleteAccount(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleResultShop(dataResult: DataResult<List<Shop>>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessShop(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleResultStamp(dataResult: DataResult<List<Review>?>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessStamp(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSuccessUser(data: User) {
        _isLoading.value = Event(false)
        _myUser.value = data
    }

    private fun handleSuccessDeleteAccount(data: Unit) {
        _isLoading.value = Event(false)

    }

    private fun handleSuccessReview(data: List<Review>) {
        _isLoading.value = Event(false)
        _myUserReview.value = data
    }

    private fun handleSuccessShop(data: List<Shop>) {
        _isLoading.value = Event(false)
        _myUserShop.value = data
    }

    private fun handleSuccessStamp(data: List<Review>?) {
        _isLoading.value = Event(false)
        _myUserStamp.value = data!!
    }

    companion object {
        val TAG: String = MyPageViewModel::class.java.simpleName
    }
}