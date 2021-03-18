package com.shop.zerobin.data.repository.shop

import com.shop.zerobin.data.source.remote.RetrofitObject
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Shop
import com.shop.zerobin.domain.entity.ShopDetail
import com.shop.zerobin.domain.mapper.DataToEntityExtension.hashtagDataToEntity
import com.shop.zerobin.domain.mapper.DataToEntityExtension.map
import com.shop.zerobin.domain.mapper.DataToEntityExtension.shopDataToEntity
import com.shop.zerobin.domain.mapper.EntityToDataExtension.shopListEntityToData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ShopRepository {
    private val zerobinClient = RetrofitObject.provideZerobinApi()

    suspend fun getShopList(type: List<Int>): Flow<DataResult<Pair<List<String>, List<Shop>>>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getShopList(shopListEntityToData(type))

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            val hashTag = response.result.hashtag.map(::hashtagDataToEntity)
            val shopList = response.result.shop.map(::shopDataToEntity)
            emit(DataResult.Success(hashTag to shopList))
        }
    }

    suspend fun getShopDetail(shopIndex: Int): Flow<DataResult<ShopDetail>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getShopDetail(shopIndex)

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            val result = zerobinClient.getShopDetail(shopIndex).result.map()
            emit(DataResult.Success(result))
        }
    }
}
