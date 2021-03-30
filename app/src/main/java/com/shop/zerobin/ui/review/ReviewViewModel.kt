package com.shop.zerobin.ui.review

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.zerobin.data.repository.shop.ReviewRepository
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.ui.common.BaseViewModel
import com.shop.zerobin.ui.common.Event
import com.shop.zerobin.ui.home.HomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ReviewViewModel(private val reviewRepository: ReviewRepository) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>> = _reviewList


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