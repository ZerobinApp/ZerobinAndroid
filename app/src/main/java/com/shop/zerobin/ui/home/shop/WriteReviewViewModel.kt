package com.shop.zerobin.ui.home.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.shop.zerobin.data.repository.shop.ReviewRepository
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Shop
import com.shop.zerobin.ui.common.BaseViewModel
import com.shop.zerobin.ui.common.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WriteReviewViewModel(private val reviewRepository: ReviewRepository) : BaseViewModel() {

    private val _seed = MutableLiveData<Boolean>(false)
    val seed: LiveData<Boolean> = _seed

    private val _shop = MutableLiveData<Shop>()
    val shop: LiveData<Shop> = _shop

    private val _imageList = MutableLiveData<List<String>>()
    val imageList: LiveData<List<String>> = _imageList

    private val _imageUrlList = MutableLiveData<List<String>>()
    val imageUrlList: LiveData<List<String>> = _imageUrlList

    val inputText = MutableLiveData("")

    val reviewGuideVisible: LiveData<Boolean> = Transformations.map(inputText) {
        it.isEmpty()
    }

    private val _inputCheckComplete = MutableLiveData<Event<Unit>>()
    val inputCheckComplete: LiveData<Event<Unit>> = _inputCheckComplete

    private val _successEvent = MutableLiveData<Event<Unit>>()
    val successEvent: LiveData<Event<Unit>> = _successEvent

    fun bloomSeed() {
        _seed.value = true
    }

    fun setShop(shop: Shop) {
        _shop.value = shop
    }

    fun onClickComplete() {
        if (!inputCheck()) return

        _inputCheckComplete.value = Event(Unit)
    }

    private fun inputCheck(): Boolean {
        if (inputText.value?.length!! < 10) {
            _isError.value = Event("후기를 최소 10자 이상 입력해주세요.")
            return false
        }
        return true
    }

    fun postReview(hashTagList: List<Int>) {
        viewModelScope.launch {
            shop.value?.shopIndex?.let { shopIndex ->
                val response = reviewRepository.postReview(
                        shopIndex,
                        imageUrlList.value,
                        inputText.value,
                        hashTagList
                )
                response.collect { handleResult(it) }
            }
        }
    }

    private fun handleResult(dataResult: DataResult<Unit>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccess()
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            is DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSuccess() {
        _isLoading.value = Event(false)
        _isError.value = Event("리뷰 등록 성공")
        _successEvent.value = Event(Unit)
    }

    companion object {
        private val TAG: String = WriteReviewViewModel::class.java.simpleName
    }
}