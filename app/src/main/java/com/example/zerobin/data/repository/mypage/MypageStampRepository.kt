package com.example.zerobin.data.repository.mypage

import com.example.zerobin.data.source.remote.RetrofitObject
import com.example.zerobin.domain.DataResult
import com.example.zerobin.domain.entity.Review
import com.example.zerobin.domain.mapper.DataToEntityExtension
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MypageStampRepository {

    private val zerobinClient = RetrofitObject.provideZerobinApi()

    suspend fun getMypageStamp(): Flow<DataResult<List<Review>?>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getMypageStamp()

            if (!response.isSuccess!!) {
                emit(DataResult.Error(Exception(response.message)))
            }

            emit(DataResult.Success(response.result?.review?.map(DataToEntityExtension::reviewDataToEntity)))
        }

    }
}