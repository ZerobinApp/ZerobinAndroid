package com.shop.zerobin.ui.review

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.zerobin.data.repository.shop.ReviewRepository
import com.shop.zerobin.data.repository.shop.ShopRepository
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Hashtag
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.ui.common.BaseViewModel
import com.shop.zerobin.ui.common.Event
import com.shop.zerobin.ui.home.HomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ReviewViewModel(private val reviewRepository: ReviewRepository, private val shopRepository: ShopRepository,) : BaseViewModel() {



    private val _hashTagList = MutableLiveData<List<Hashtag>>()
    val hashTagList: LiveData<List<Hashtag>> = _hashTagList

    private val _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>> = _reviewList

    private val _reviewDetail = MutableLiveData<Review>()
    val reviewDetail: LiveData<Review> = _reviewDetail


    fun setHashTagList() {
        viewModelScope.launch {
            val response = shopRepository.getHashTag()
            response.collect { handleHashTagResult(it) }
        }
    }

    private fun handleHashTagResult(dataResult: DataResult<List<Hashtag>>) {
        when (dataResult) {
            is DataResult.Success -> handleHashTagSuccess(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            is DataResult.Loading -> handleLoading()
        }
    }

    private fun handleHashTagSuccess(data: List<Hashtag>) {
        _isLoading.value = Event(false)
        _hashTagList.value = data
    }


    fun requestGetReview(shopIndex: Int,reviewIndex: Int){
        viewModelScope.launch {
            val response= reviewRepository.getReviewDetail(shopIndex,reviewIndex)
            response.collect { handleResultReviewDetail(it)
            }
        }
    }

    private fun handleResultReviewDetail(dataResult: DataResult<Review>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessReviewDetail(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSuccessReviewDetail(data: Review) {
        _reviewDetail.value=data
    }


    fun requestReviewList(hashtagList: List<Int>) {
        //코루틴 사용....
        viewModelScope.launch {
            val response = reviewRepository.getReviewList(hashtagList)
            Log.d(HomeViewModel.TAG, response.toString())
            response.collect { handleResult(it) }
        }
    }


    fun requestReviewReport(reviewIndex: Int) {
        viewModelScope.launch {
            val response = reviewRepository.reportReview(reviewIndex)
            Log.d(TAG, response.toString())
            response.collect { handleResultReport(it) }
        }

    }


    fun requestReviewDelete(shopIndex: Int, reviewIndex: Int) {
        viewModelScope.launch {
            val response = reviewRepository.deleteReview(shopIndex, reviewIndex)
            Log.d(TAG, response.toString())
            response.collect { handleResultDelete(it) }
        }
    }

    private fun handleResultReport(dataResult: DataResult<Unit>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessReport(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleResultDelete(dataResult: DataResult<Unit>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessDelete(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleResult(dataResult: DataResult<List<Review>>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccess(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }


    private fun handleSuccess(data: List<Review>) {
        _isLoading.value = Event(false)
        _reviewList.value = data
    }

    private fun handleSuccessReport(data: Unit) {
        _isLoading.value = Event(false)
        _isError.value = Event("신고 완료되었습니다.")
        Log.d("신고", data.toString())
    }

    private fun handleSuccessDelete(data: Unit) {
        _isLoading.value = Event(false)
        _isError.value = Event("삭제 완료되었습니다.")
        Log.d("삭제", data.toString())
        requestReviewList(emptyList())
    }

    companion object {
        val TAG: String = ReviewViewModel::class.java.simpleName
    }
}