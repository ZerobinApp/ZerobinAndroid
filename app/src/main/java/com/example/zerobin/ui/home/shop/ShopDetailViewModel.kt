package com.example.zerobin.ui.home.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zerobin.model.Review
import com.example.zerobin.model.Shop
import com.example.zerobin.model.ShopDetail

class ShopDetailViewModel : ViewModel() {

    private val _shopDetail = MutableLiveData<ShopDetail>()
    val shopDetail: LiveData<ShopDetail> = _shopDetail

    fun requestShopDetailData(shop: Shop) {
        val tempFeature1 = ShopDetail.Feature("", "특징번호 1 텍스트", "특징번호 1에 대한 부가설명")
        val tempFeature2 = ShopDetail.Feature("", "특징번호 2 텍스트", "특징번호 2에 대한 부가설명")
        val tempFeature3 = ShopDetail.Feature("", "특징번호 3 텍스트", "특징번호 3에 대한 부가설명")

        val tempReview1 = Review("땡상점", "ㅋㅋㅋ", true, "22229988", "asdfasfasfasdfsdf", "asdfad")
        val tempReview2 = Review("호상점", "ㅋㅋㅋ", true, "22229988", "asdfasfasfasdfsdf", "asdfad")

        _shopDetail.value = ShopDetail(
            name = shop.name,
            hashTag = shop.hashTag,
            favorite = shop.favorite,
            address = shop.address,
            imageUrlList = listOf(shop.imageUrl),
            featureList = listOf(tempFeature1, tempFeature2, tempFeature3),
            shopDescription = "알맹상점은 용산구 이태원로123-45 한적한 골목에 위치한\n제로웨이스트 매장입니다. 블라블라 블라블라를 판매하고,\n일회용품과 블라블라 사용을 최소로\n하는것을 목표하고 있어요.블라블라 블라블라!",
            reviewList = arrayListOf(tempReview1, tempReview2),
        )
    }


}