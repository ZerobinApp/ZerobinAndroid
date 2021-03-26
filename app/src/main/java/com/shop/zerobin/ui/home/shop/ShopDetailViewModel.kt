package com.shop.zerobin.ui.home.shop

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.zerobin.data.repository.shop.ShopRepository
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Shop
import com.shop.zerobin.domain.entity.ShopDetail
import com.shop.zerobin.ui.common.BaseViewModel
import com.shop.zerobin.ui.common.Event
import com.shop.zerobin.ui.home.HomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ShopDetailViewModel(private val shopRepository: ShopRepository) : BaseViewModel() {

    private val _shopDetail = MutableLiveData<ShopDetail>()
    val shopDetail: LiveData<ShopDetail> = _shopDetail

    private val _zzimSuccess = MutableLiveData<Event<Boolean>>()
    val zzimSuccess: LiveData<Event<Boolean>> = _zzimSuccess

    fun requestShopDetailData(shop: Shop) {
        _isLogin.value = shopRepository.isLogin()
        viewModelScope.launch {
            val response = shopRepository.getShopDetail(shop.shopIndex)
            Log.d(TAG, response.toString())
            response.collect { handleResult(it) }
        }
    }

    fun requestZzimShop(shopIndex: Int, zzim: Boolean) {
        viewModelScope.launch {
            val response = shopRepository.zzimShop(shopIndex)
            response.collect { handleZzimShopResult(it, zzim) }
        }
    }

    private fun handleResult(dataResult: DataResult<ShopDetail>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccess(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSuccess(data: ShopDetail) {
        _isLoading.value = Event(false)
        _shopDetail.value = data
    }

    private fun handleZzimShopResult(dataResult: DataResult<Unit>, zzim: Boolean) {
        when (dataResult) {
            is DataResult.Success -> handleZzimShopSuccess(zzim)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            is DataResult.Loading -> handleLoading()
        }
    }

    private fun handleZzimShopSuccess(zzim: Boolean) {
        Log.d(HomeViewModel.TAG, "매장 찜 성공")
        _isLoading.value = Event(false)
        _zzimSuccess.value = Event(zzim)
    }

    companion object {
        val TAG: String = ShopDetailViewModel::class.java.simpleName
    }
}