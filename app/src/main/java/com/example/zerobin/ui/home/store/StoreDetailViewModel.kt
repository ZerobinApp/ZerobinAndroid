package com.example.zerobin.ui.home.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zerobin.model.Store
import com.example.zerobin.model.StoreDetail

class StoreDetailViewModel : ViewModel() {

    private val _storeDetail = MutableLiveData<StoreDetail>()
    val storeDetail: LiveData<StoreDetail> = _storeDetail

    fun requestStoreDetailData(store: Store) {
        val tempFeature1 = StoreDetail.Feature("", "특징번호 1 텍스트", "특징번호 1에 대한 부가설명")
        val tempFeature2 = StoreDetail.Feature("", "특징번호 2 텍스트", "특징번호 2에 대한 부가설명")
        val tempFeature3 = StoreDetail.Feature("", "특징번호 3 텍스트", "특징번호 3에 대한 부가설명")

        _storeDetail.value = StoreDetail(
            name = store.name,
            hashTag = store.hashTag,
            favorite = store.favorite,
            address = store.address,
            imageUrlList = listOf(store.imageUrl),
            featureList = listOf(tempFeature1, tempFeature2, tempFeature3),
            storeDescription = "알맹상점은 용산구 이태원로123-45 한적한 골목에 위치한\n제로웨이스트 매장입니다. 블라블라 블라블라를 판매하고,\n일회용품과 블라블라 사용을 최소로\n하는것을 목표하고 있어요.블라블라 블라블라!",
            reviewList = listOf(),
        )
    }
}