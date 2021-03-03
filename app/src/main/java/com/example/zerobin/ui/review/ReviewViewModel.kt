package com.example.zerobin.ui.review

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zerobin.data.repository.shop.ReviewRepository
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
            val data = reviewRepository.getReviewList()
            Log.d(HomeViewModel.TAG, data.toString())
            _reviewList.value = data

        }



    }
}