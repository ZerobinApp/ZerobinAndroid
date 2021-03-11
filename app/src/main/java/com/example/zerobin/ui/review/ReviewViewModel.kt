package com.example.zerobin.ui.review

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.zerobin.data.repository.shop.ReviewRepository
import com.example.zerobin.domain.DataResult
import com.example.zerobin.domain.entity.Review
import com.example.zerobin.ui.common.BaseViewModel
import com.example.zerobin.ui.common.Event
import com.example.zerobin.ui.home.HomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ReviewViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>> = _reviewList

    private val reviewRepository = ReviewRepository()

    fun requestReviewList() {
        //코루틴 사용....
        viewModelScope.launch {
            val response = reviewRepository.getReviewList()
            Log.d(HomeViewModel.TAG, response.toString())
            response.collect { handleResult(it) }
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

    companion object {
        val TAG: String = ReviewViewModel::class.java.simpleName
    }
}