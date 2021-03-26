package com.shop.zerobin.data.repository.shop

import android.content.Context
import com.shop.zerobin.data.repository.mypage.MyPageRepository
import com.shop.zerobin.data.source.remote.RetrofitObject
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.domain.mapper.DataToEntityExtension
import com.shop.zerobin.domain.mapper.EntityToDataExtension.postReviewEntityToData
import com.shop.zerobin.domain.mapper.EntityToDataExtension.reviewListEntityToData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReviewRepository(val context: Context) {

    private val pref =
        context.getSharedPreferences(MyPageRepository.PREF_DEFAULT, Context.MODE_PRIVATE)

    private val zerobinClient
        get() = RetrofitObject.provideZerobinApi(getJWT())

    private fun getJWT() = pref.getString(MyPageRepository.PREF_JWT, "") ?: ""

    suspend fun getReviewList(type: List<Int>): Flow<DataResult<List<Review>>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getReviewList(reviewListEntityToData(type))

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            val reviewList = response.result.review.map(DataToEntityExtension::reviewDataToEntity)

            emit(DataResult.Success(reviewList))

        }
    }

    suspend fun reportReview(reviewIndex: Int): Flow<DataResult<Unit>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.reportReview(reviewIndex)

            if (response.isSuccess != true) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            emit(DataResult.Success(Unit))
        }
    }

    suspend fun deleteReview(reviewIndex: Int, shopIndex: Int): Flow<DataResult<Unit>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.deleteReview(reviewIndex, shopIndex)

            if (response.isSuccess != true) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            emit(DataResult.Success(Unit))
        }
    }

    suspend fun postReview(
        shopIndex: Int,
        imageUrlList: List<String>?,
        inputText: String?,
        hashTagList: List<Int>?,
    ): Flow<DataResult<Unit>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.postReview(
                shopIndex,
                postReviewEntityToData(imageUrlList, inputText, hashTagList)
            )

            if (response.isSuccess != true) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            emit(DataResult.Success(Unit))
        }
    }
}