package com.shop.zerobin.ui.splash.filter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.zerobin.data.repository.shop.ShopRepository
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Hashtag
import com.shop.zerobin.ui.common.BaseViewModel
import com.shop.zerobin.ui.common.Event
import com.shop.zerobin.ui.home.HomeViewModel
import com.shop.zerobin.ui.review.ReviewViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FilterViewModel(private val shopRepository: ShopRepository) : BaseViewModel() {

    private val _hashtagList = MutableLiveData<List<Hashtag>>()
    val hashtagList: LiveData<List<Hashtag>> = _hashtagList

    fun requestFilterList() {
        viewModelScope.launch {
            val response = shopRepository.getHashtag()
            Log.d(HomeViewModel.TAG, response.toString())
            response.collect { handleResult(it) }
        }
    }

    private fun handleResult(dataResult: DataResult<List<Hashtag>>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccess(dataResult.data)
            is DataResult.Error -> handleError(ReviewViewModel.TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSuccess(data: List<Hashtag>) {
        _isLoading.value = Event(false)
        _hashtagList.value = data
    }
}