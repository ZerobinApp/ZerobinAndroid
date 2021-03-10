package com.example.zerobin.data.repository.mypage

import com.example.zerobin.data.source.remote.RetrofitObject
import com.example.zerobin.domain.DataResult
import com.example.zerobin.domain.entity.Review
import com.example.zerobin.domain.entity.Shop
import com.example.zerobin.domain.entity.User
import com.example.zerobin.domain.mapper.DataToEntityExtension
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MyPageRepository {

    private val zerobinClient = RetrofitObject.provideZerobinApi()

    suspend fun getMyPageReview(): Flow<DataResult<List<Review>>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getMyPageReview()

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
            }

            emit(DataResult.Success(response.result.review.map(DataToEntityExtension::reviewDataToEntity)))
        }

    }

    suspend fun getMyPageShop(): Flow<DataResult<List<Shop>>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getMyPageShop()

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
            }

            emit( DataResult.Success(response.result.shop.map(DataToEntityExtension::myPageShopDataToEntity)))
        }

    }

    suspend fun getMyPageStamp(): Flow<DataResult<List<Review>>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getMyPageStamp()

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
            }

            emit(DataResult.Success(response.result.review.map(DataToEntityExtension::reviewDataToEntity)))
        }

    }

    suspend fun getMyPageUser(): Flow<DataResult<List<User>>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getMyPageUser()

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
            }
            emit(DataResult.Success(response.result.map(DataToEntityExtension::userDataToEntity)))
        }

    }
}