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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FilterViewModel(private val shopRepository: ShopRepository) : BaseViewModel() {

    private val _hashtagList = MutableLiveData<List<Hashtag>>()
    val hashtagList: LiveData<List<Hashtag>> = _hashtagList

    fun requestFilterList() {
        viewModelScope.launch {
            val response = shopRepository.getHashTag()
            Log.d(TAG, response.toString())
            response.collect { handleResult(it) }
        }
    }

    fun saveHashTagList(hashTagList: List<Int>) {
        shopRepository.saveHashTagList(hashTagList)
    }

    fun getSavedHashTagList(): List<Int> {
        return shopRepository.getSavedHashTagList()
    }

    fun saveFirstEnter() {
        shopRepository.saveFirstEnter()
    }

    fun isFirstEnter(): Boolean {
        return shopRepository.isFirstEnter()
    }

    private fun handleResult(dataResult: DataResult<List<Hashtag>>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccess(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSuccess(data: List<Hashtag>) {
        _isLoading.value = Event(false)
        _hashtagList.value = data
    }

    companion object {
        val TAG: String = FilterViewModel::class.java.simpleName
    }
}