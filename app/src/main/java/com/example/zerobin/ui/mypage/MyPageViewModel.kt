package com.example.zerobin.ui.mypage


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zerobin.data.repository.mypage.MyPageRepository
import com.example.zerobin.domain.DataResult
import com.example.zerobin.domain.entity.Review
import com.example.zerobin.domain.entity.Shop
import com.example.zerobin.domain.entity.User
import com.example.zerobin.ui.home.HomeViewModel
import com.example.zerobin.ui.review.ReviewViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {


    private val _myUser = MutableLiveData<User>()
    val myUser: LiveData<User> = _myUser

    private val _myUserReview = MutableLiveData<List<Review>>()
    val myUserReview: LiveData<List<Review>> = _myUserReview

    private val _myUserShop= MutableLiveData<List<Shop>>()
    val myUserShop: LiveData<List<Shop>> = _myUserShop

    private val _myUserStamp= MutableLiveData<List<Review>>()
    val myUserStamp: LiveData<List<Review>> = _myUserStamp


    private val myPageRepository = MyPageRepository()


    fun requestMyPage() {

        viewModelScope.launch {
            val userResponse=myPageRepository.getMyPageUser()
            userResponse.collect { handleResultUser(it)}


        }


    }
    fun requestMyPageReview(){
        viewModelScope.launch {
            val response = myPageRepository.getMyPageReview()
            Log.d(HomeViewModel.TAG, response.toString())
            response.collect { handleResultReview(it)}

        }
    }


    fun requestMyPageShop(){
        viewModelScope.launch {
            val response = myPageRepository.getMyPageShop()
            Log.d(HomeViewModel.TAG, response.toString())
            response.collect { handleResultShop(it)}

        }
    }

    fun requestMyPageStamp(){
        viewModelScope.launch {
            val response = myPageRepository.getMyPageStamp()
            Log.d(HomeViewModel.TAG, response.toString())
            response.collect { handleResultStamp(it)}

        }
    }

    private fun handleResultReview(dataResult: DataResult<List<Review>>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccess(dataResult.data)
            is DataResult.Error -> handleError(dataResult.exception)
            DataResult.Loading -> {
            }
        }
    }
    private fun handleResultUser(dataResult: DataResult<List<User>>?) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessUser(dataResult.data)
            is DataResult.Error -> handleError(dataResult.exception)
            DataResult.Loading -> {
            }
        }
    }

    private fun handleResultShop(dataResult: DataResult<List<Shop>>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessShop(dataResult.data)
            is DataResult.Error -> handleError(dataResult.exception)
            DataResult.Loading -> {
            }
        }
    }
    private fun handleResultStamp(dataResult: DataResult<List<Review>?>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessStamp(dataResult.data)
            is DataResult.Error -> handleError(dataResult.exception)
            DataResult.Loading -> {
            }
        }
    }

    private fun handleSuccessUser(data: List<User>?) {
        //아직 로그인을 하기전이라 유저의 정보가 없어서 임의로 하나 생성함.
        _myUser.value = User(0,"생각이안나영",0,0)
    }

    private fun handleSuccess(data: List<Review>) {
        _myUserReview.value=data
    }

    private fun handleSuccessShop(data: List<Shop>) {
        _myUserShop.value = data
    }

    private fun handleSuccessStamp(data: List<Review>?) {
        _myUserStamp.value = data!!
    }
    private fun handleError(exception: Exception) {
        Log.d(TAG, exception.message ?: "")
    }

    companion object {
        val TAG: String = MyPageViewModel::class.java.simpleName
    }


}