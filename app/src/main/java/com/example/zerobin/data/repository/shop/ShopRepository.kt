package com.example.zerobin.data.repository.shop

import com.example.zerobin.data.source.remote.RetrofitObject
import com.example.zerobin.domain.entity.Shop
import com.example.zerobin.domain.mapper.DataToEntityExtension.hashtagDataToEntity
import com.example.zerobin.domain.mapper.DataToEntityExtension.shopDataToEntity
import com.example.zerobin.domain.mapper.EntityToDataExtension.shopListEntityToData

class ShopRepository {
    private val zerobinClient = RetrofitObject.provideZerobinApi()

    suspend fun getShopList(type: List<Int>): Pair<List<String>, List<Shop>> {
        val response = zerobinClient.getShopList(shopListEntityToData(type))

        val hashTag = response.result.hashtag.map(::hashtagDataToEntity)
        val shopList = response.result.shop.map(::shopDataToEntity)

        return Pair(hashTag, shopList)
    }
}
