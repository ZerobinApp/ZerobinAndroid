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
import kotlinx.coroutines.launch

class ShopDetailViewModel : ViewModel() {

    private val shopRepository = ShopRepository()

    private val _shopDetail = MutableLiveData<ShopDetail>()
    val shopDetail: LiveData<ShopDetail> = _shopDetail

    fun requestShopDetailData(shop: Shop) {
        viewModelScope.launch {
            val response = shopRepository.getShopDetail(shop.shopIndex)
            Log.d(TAG, response.toString())

            when (response) {
                is DataResult.Success -> handleSuccess(response.data)
                is DataResult.Error -> handleError(response.exception)
                DataResult.Loading -> {
                }
            }
        }
    }

    private fun handleSuccess(data: ShopDetail) {
        _shopDetail.value = data
    }

    private fun handleError(exception: Exception) {
        Log.e(TAG, exception.message ?: "handleError")
    }

    companion object {
        val TAG: String = ShopDetailViewModel::class.java.simpleName
    }
}