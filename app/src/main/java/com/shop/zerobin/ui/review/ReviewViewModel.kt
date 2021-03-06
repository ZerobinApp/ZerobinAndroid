package com.shop.zerobin.ui.review

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.zerobin.data.repository.shop.ReviewRepository
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Hashtag
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.ui.common.BaseViewModel
import com.shop.zerobin.ui.common.Event
import com.shop.zerobin.ui.home.HomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ReviewViewModel(private val reviewRepository: ReviewRepository) : BaseViewModel() {

    private val _hashTagList = MutableLiveData<List<Hashtag>>()
    val hashTagList: LiveData<List<Hashtag>> = _hashTagList

    private val _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>> = _reviewList

    fun requestReviewList(hashTagList: List<Int>) {
        viewModelScope.launch {
            val response = reviewRepository.getReviewList(hashTagList)
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
        Log.d(TAG, data.toString())
    }

    private fun handleSuccessDelete(data: Unit) {
        _isLoading.value = Event(false)
        _isError.value = Event("삭제 완료되었습니다.")
        Log.d(TAG, data.toString())
        requestReviewList(emptyList())
    }

    companion object {
        val TAG: String = ReviewViewModel::class.java.simpleName
    }
}