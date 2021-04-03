package com.shop.zerobin.ui.home.shop

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.zerobin.data.repository.shop.ReviewRepository
import com.shop.zerobin.data.repository.shop.ShopRepository
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.ShopDetail
import com.shop.zerobin.ui.common.BaseViewModel
import com.shop.zerobin.ui.common.Event
import com.shop.zerobin.ui.home.HomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ShopDetailViewModel(
    private val shopRepository: ShopRepository,
    private val reviewRepository: ReviewRepository,
) : BaseViewModel() {

    private val _shopDetail = MutableLiveData<ShopDetail>()
    val shopDetail: LiveData<ShopDetail> = _shopDetail

    private val _zzimSuccess = MutableLiveData<Event<Boolean>>()
    val zzimSuccess: LiveData<Event<Boolean>> = _zzimSuccess

    fun requestShopDetailData(shopIndex: Int) {
        _isLogin.value = shopRepository.isLogin()
        viewModelScope.launch {
            val response = shopRepository.getShopDetail(shopIndex)
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

    fun requestReviewDelete(reviewIndex: Int) {
        viewModelScope.launch {
            val response =
                reviewRepository.deleteReview(_shopDetail.value?.shopIndex ?: 0, reviewIndex)
            Log.d(TAG, response.toString())
            response.collect { handleResultDelete(it) }
        }
    }

    fun requestReviewReport(reviewIndex: Int) {
        viewModelScope.launch {
            val response = reviewRepository.reportReview(reviewIndex)
            Log.d(TAG, response.toString())
            response.collect { handleResultReport(it) }
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

    private fun handleResultDelete(dataResult: DataResult<Unit>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessDelete(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSuccessDelete(data: Unit) {
        _isLoading.value = Event(false)
        _isError.value = Event("삭제 완료되었습니다.")
        Log.d(TAG, data.toString())
        requestShopDetailData(_shopDetail.value?.shopIndex ?: return)
    }

    private fun handleResultReport(dataResult: DataResult<Unit>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessReport(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSuccessReport(data: Unit) {
        _isLoading.value = Event(false)
        _isError.value = Event("신고 완료되었습니다.")
    }

    companion object {
        val TAG: String = ShopDetailViewModel::class.java.simpleName
    }
}