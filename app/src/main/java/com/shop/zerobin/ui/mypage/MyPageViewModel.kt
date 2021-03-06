package com.shop.zerobin.ui.mypage


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.zerobin.data.repository.mypage.MyPageRepository
import com.shop.zerobin.data.repository.shop.ReviewRepository
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.domain.entity.Shop
import com.shop.zerobin.domain.entity.User
import com.shop.zerobin.ui.common.BaseViewModel
import com.shop.zerobin.ui.common.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val myPageRepository: MyPageRepository,
    private val reviewRepository: ReviewRepository,
) : BaseViewModel() {
    val inputNickName = MutableLiveData("")

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
            _isError.value = Event("닉네임을 입력해주세요.")
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
            Log.d(TAG, response.toString())
            response.collect { handleResultReview(it) }
        }
    }

    fun requestMyPageShop() {
        viewModelScope.launch {
            val response = myPageRepository.getMyPageShop()
            Log.d(TAG, response.toString())
            response.collect { handleResultShop(it) }
        }
    }

    fun requestMyPageStamp() {
        viewModelScope.launch {
            val response = myPageRepository.getMyPageStamp()
            Log.d(TAG, response.toString())
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

    fun requestReviewDelete(shopIndex: Int, reviewIndex: Int) {
        viewModelScope.launch {
            val response =
                reviewRepository.deleteReview(shopIndex, reviewIndex)
            Log.d(TAG, response.toString())
            response.collect { handleResultDelete(it) }
        }
    }

    private fun handleResultDelete(dataResult: DataResult<Unit>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessDelete(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSuccessDelete(data: Unit) {
        _isLoading.value = Event(false)
        _isError.value = Event("삭제 완료되었습니다.")
        Log.d(TAG, data.toString())
        requestMyPageReview()
        requestMyPageStamp()
    }

    companion object {
        val TAG: String = MyPageViewModel::class.java.simpleName
    }
}