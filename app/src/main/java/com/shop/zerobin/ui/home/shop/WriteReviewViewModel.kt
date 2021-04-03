package com.shop.zerobin.ui.home.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.shop.zerobin.data.repository.shop.ReviewRepository
import com.shop.zerobin.data.repository.shop.ShopRepository
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Hashtag
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.domain.entity.Shop
import com.shop.zerobin.ui.common.BaseViewModel
import com.shop.zerobin.ui.common.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WriteReviewViewModel(
    private val reviewRepository: ReviewRepository,
    private val shopRepository: ShopRepository,
) : BaseViewModel() {

    private val _seed = MutableLiveData<Boolean>(false)
    val seed: LiveData<Boolean> = _seed

    private val _shopIndex = MutableLiveData<Int>()
    val shopIndex: LiveData<Int> = _shopIndex

    private val _imageUrlList = MutableLiveData<List<String>>(emptyList())
    val imageUrlList: LiveData<List<String>> = _imageUrlList

    private val _deletedImage = MutableLiveData<List<String>>(emptyList())
    val deletedImage: LiveData<List<String>> = _deletedImage

    private val _imageCount = MutableLiveData(0)
    val imageCount: LiveData<Int> = _imageCount

    private val _hashTagList = MutableLiveData<List<Hashtag>>()
    val hashTagList: LiveData<List<Hashtag>> = _hashTagList

    private val _reviewDetail = MutableLiveData<Review>()
    val reviewDetail: LiveData<Review> = _reviewDetail

    val inputText = MutableLiveData("")

    private val _isModifyState = MutableLiveData(false)
    val isModifyState: LiveData<Boolean> = _isModifyState

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

    fun setShopIndex(shopIndex: Int) {
        _shopIndex.value = shopIndex
    }

    fun setHashTagList() {
        viewModelScope.launch {
            val response = shopRepository.getHashTag()
            response.collect { handleHashTagResult(it) }
        }
    }

    fun plusImageCount() {
        _imageCount.value = _imageCount.value?.plus(1)
    }

    fun minusImageCount() {
        _imageCount.value = _imageCount.value?.minus(1)
    }

    fun addImageUrl(url: String) {
        _imageUrlList.value = _imageUrlList.value?.plus(url)
    }

    fun onSeedClick() {
        _seed.value = _seed.value?.not()
    }

    fun onClickComplete() {
        if (!inputCheck()) return

        _inputCheckComplete.value = Event(Unit)
    }

    private fun inputCheck(): Boolean {
        if (inputText.value?.length!! < 10) {
            _isError.value = Event("리뷰를 최소 10자 이상 입력해주세요.")
            return false
        }
        return true
    }

    fun postReview(hashTagList: List<Int>) {
        viewModelScope.launch {
            shopIndex.value?.let { shopIndex ->
                val response = reviewRepository.postReview(
                    shopIndex,
                    imageUrlList.value,
                    inputText.value,
                    hashTagList,
                    _seed.value
                )
                response.collect { handleResult(it) }
            }
        }
    }

    fun requestGetReview(shopIndex: Int, reviewIndex: Int) {
        _isModifyState.value = true
        _shopIndex.value = shopIndex
        viewModelScope.launch {
            val response = reviewRepository.getReviewDetail(shopIndex, reviewIndex)
            response.collect { handleResultReviewDetail(it) }
        }
    }

    fun requestModifyReview(
        hashTagList: List<Int>,
    ) {
        viewModelScope.launch {
            val response = reviewRepository.setReviewDetail(
                shopIndex = _shopIndex.value ?: return@launch,
                reviewIndex = _reviewDetail.value?.reviewIndex ?: return@launch,
                comment = inputText.value,
                deletedHashTagList = _reviewDetail.value?.hashTagList?.map { it.hashtagIndex },
                updatedHashTagList = hashTagList,
                deletedImageList = _deletedImage.value,
                updatedImageList = imageUrlList.value,
                stamp = _seed.value
            )
            response.collect { handleResultModify(it) }
        }
    }

    fun addDeleteImage(imgUrl: String) {
        if (_deletedImage.value?.contains(imgUrl) != true) {
            _deletedImage.value = _deletedImage.value?.plus(imgUrl)
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

    private fun handleHashTagResult(dataResult: DataResult<List<Hashtag>>) {
        when (dataResult) {
            is DataResult.Success -> handleHashTagSuccess(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            is DataResult.Loading -> handleLoading()
        }
    }

    private fun handleHashTagSuccess(data: List<Hashtag>) {
        _isLoading.value = Event(false)
        _hashTagList.value = data
    }

    private fun handleResultReviewDetail(dataResult: DataResult<Review>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessReviewDetail(dataResult.data)
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSuccessReviewDetail(data: Review) {
        _reviewDetail.value = data
        inputText.value = data.comment
        _seed.value = data.stamp
        _imageCount.value = data.imageList.size
    }

    private fun handleResultModify(dataResult: DataResult<Unit>) {
        when (dataResult) {
            is DataResult.Success -> handleSuccessModify()
            is DataResult.Error -> handleError(TAG, dataResult.exception)
            is DataResult.Loading -> handleLoading()
        }
    }

    private fun handleSuccessModify() {
        _isLoading.value = Event(false)
        _isError.value = Event("리뷰 수정 성공")
        _successEvent.value = Event(Unit)
    }

    companion object {
        private val TAG: String = WriteReviewViewModel::class.java.simpleName
    }
}