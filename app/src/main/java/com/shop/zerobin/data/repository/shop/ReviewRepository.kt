package com.shop.zerobin.data.repository.shop

import com.shop.zerobin.data.source.remote.RetrofitObject
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.domain.mapper.DataToEntityExtension.reviewDataToEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReviewRepository {

    private val zerobinClient = RetrofitObject.provideZerobinApi()

    suspend fun getReviewList(): Flow<DataResult<List<Review>>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getReviewList()

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            emit(DataResult.Success(response.result.review.map(::reviewDataToEntity)))
        }

    }
}