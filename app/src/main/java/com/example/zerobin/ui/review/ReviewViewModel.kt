package com.example.zerobin.ui.review

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zerobin.data.repository.shop.ReviewRepository
import com.example.zerobin.domain.DataResult
import com.example.zerobin.domain.entity.Review
import com.example.zerobin.ui.home.HomeViewModel
import kotlinx.coroutines.launch

class ReviewViewModel : ViewModel() {

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

            when (response) {
                is DataResult.Success -> handleSuccess(response.data)
                is DataResult.Error -> handleError(response.exception)
                DataResult.Loading -> {
                }
            }


        }



    }

    private fun handleSuccess(data: List<Review>){
        _reviewList.value = data
    }
    private fun handleError(exception: Exception){
        Log.d(TAG,exception.message ?:"")
    }

    companion object {
        val TAG: String=ReviewViewModel::class.java.simpleName
    }
}