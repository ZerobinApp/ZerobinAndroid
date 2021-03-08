package com.example.zerobin.data.repository.shop

import com.example.zerobin.data.source.remote.RetrofitObject
import com.example.zerobin.domain.DataResult
import com.example.zerobin.domain.entity.Shop
import com.example.zerobin.domain.entity.ShopDetail
import com.example.zerobin.domain.mapper.DataToEntityExtension.hashtagDataToEntity
import com.example.zerobin.domain.mapper.DataToEntityExtension.map
import com.example.zerobin.domain.mapper.DataToEntityExtension.shopDataToEntity
import com.example.zerobin.domain.mapper.EntityToDataExtension.shopListEntityToData

class ShopRepository {
    private val zerobinClient = RetrofitObject.provideZerobinApi()

    suspend fun getShopList(type: List<Int>): DataResult<Pair<List<String>, List<Shop>>> {
        val response = zerobinClient.getShopList(shopListEntityToData(type))

        if (!response.isSuccess) {
            return DataResult.Error(Exception(response.message))
        }
        val hashTag = response.result.hashtag.map(::hashtagDataToEntity)
        val shopList = response.result.shop.map(::shopDataToEntity)

        return DataResult.Success(hashTag to shopList)
    }

    suspend fun getShopDetail(shopIndex: Int): DataResult<ShopDetail> {
        val response = zerobinClient.getShopDetail(shopIndex)

        if (!response.isSuccess) {
            return DataResult.Error(Exception(response.message))
        }

        val result = zerobinClient.getShopDetail(shopIndex).result.map()
        return DataResult.Success(result)
    }
}
