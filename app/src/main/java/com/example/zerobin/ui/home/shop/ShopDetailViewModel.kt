package com.example.zerobin.ui.home.shop

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zerobin.data.repository.shop.ShopRepository
import com.example.zerobin.domain.DataResult
import com.example.zerobin.domain.entity.Shop
import com.example.zerobin.domain.entity.ShopDetail
import com.example.zerobin.ui.common.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ShopDetailViewModel : ViewModel() {

    private val shopRepository = ShopRepository()

    private val _shopDetail = MutableLiveData<ShopDetail>()
    val shopDetail: LiveData<ShopDetail> = _shopDetail

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    fun requestShopDetailData(shop: Shop) {
        viewModelScope.launch {
            val response = shopRepository.getShopDetail(shop.shopIndex)
            Log.d(TAG, response.toString())
            response.collect { handleResult(it) }
        }
    }

    private fun handleResult(dataResult: DataResult<ShopDetail>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccess(dataResult.data)
            is DataResult.Error -> handleError(dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSuccess(data: ShopDetail) {
        _shopDetail.value = data
        _isLoading.value = Event(false)
    }

    private fun handleError(exception: Exception) {
        Log.e(TAG, exception.message ?: "handleError")
        _isLoading.value = Event(false)
    }

    private fun handleLoading() {
        _isLoading.value = Event(true)
    }

    companion object {
        val TAG: String = ShopDetailViewModel::class.java.simpleName
    }
}