package com.shop.zerobin.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.zerobin.data.repository.shop.ShopRepository
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Shop
import com.shop.zerobin.ui.common.BaseViewModel
import com.shop.zerobin.ui.common.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val shopRepository: ShopRepository) : BaseViewModel() {

    private val _hashtagList = MutableLiveData<String>()
    val hashtagList: LiveData<String> = _hashtagList

    private val _shopList = MutableLiveData<List<Shop>>()
    val shopList: LiveData<List<Shop>> = _shopList

    fun requestShopList(hashtagList: List<Int>) {
        viewModelScope.launch {
            val response = shopRepository.getShopList(hashtagList)
            Log.d(TAG, response.toString())
            response.collect { handleResult(it) }
        }
    }

    fun requestSearchShopList(name: String) {
        viewModelScope.launch {
            val response = shopRepository.getSearchShopList(name)
            response.collect { handleSearchShopResult(it) }
        }
    }

    private fun handleSearchShopResult(dataResult: DataResult<List<Shop>>) {
        when (dataResult) {
            is DataResult.Success -> handleSearchShopSuccess(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSearchShopSuccess(data: List<Shop>) {
        _isLoading.value = Event(false)
        _shopList.value = data
    }

    private fun handleResult(dataResult: DataResult<Pair<List<String>, List<Shop>>>) {
        Log.d(TAG, dataResult.toString())
        when (dataResult) {
            is DataResult.Success -> handleSuccess(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSuccess(data: Pair<List<String>, List<Shop>>) {
        _isLoading.value = Event(false)
        var hashTagList = ""
        data.first.forEach {
            hashTagList += "#$it  "
        }
        _hashtagList.value = hashTagList
        _shopList.value = data.second
    }

    companion object {
        val TAG: String = HomeViewModel::class.java.simpleName
    }
}