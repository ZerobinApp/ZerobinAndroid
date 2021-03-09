package com.example.zerobin.data.repository.mypage

import com.example.zerobin.data.source.remote.RetrofitObject
import com.example.zerobin.domain.DataResult
import com.example.zerobin.domain.entity.Shop
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MypageShopRepository {

    private val zerobinClient = RetrofitObject.provideZerobinApi()

    suspend fun getMypageShop(): Flow<DataResult<List<Shop>>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getMypageShop()

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
            }

            emit(response.result.let { DataResult.Success(it.shop) })
        }

    }
}