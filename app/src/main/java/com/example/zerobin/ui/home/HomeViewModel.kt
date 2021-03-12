package com.example.zerobin.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.zerobin.data.repository.shop.ShopRepository
import com.example.zerobin.domain.DataResult
import com.example.zerobin.domain.entity.Shop
import com.example.zerobin.ui.common.BaseViewModel
import com.example.zerobin.ui.common.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    private val shopRepository = ShopRepository()

    private val _hashtagList = MutableLiveData<String>()
    val hashtagList: LiveData<String> = _hashtagList

    private val _shopList = MutableLiveData<List<Shop>>()
    val shopList: LiveData<List<Shop>> = _shopList

    fun requestShopList() {
        viewModelScope.launch {
            val response = shopRepository.getShopList(listOf(1, 2))
            Log.d(TAG, response.toString())
            response.collect { handleResult(it) }
        }
    }

    private fun handleResult(dataResult: DataResult<Pair<List<String>, List<Shop>>>) {
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