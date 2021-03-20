package com.shop.zerobin.data.repository.shop

import android.content.Context
import com.shop.zerobin.data.repository.mypage.MyPageRepository
import com.shop.zerobin.data.source.remote.RetrofitObject
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.domain.mapper.DataToEntityExtension.reviewDataToEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReviewRepository(val context: Context) {

    private val pref =
        context.getSharedPreferences(MyPageRepository.PREF_DEFAULT, Context.MODE_PRIVATE)

    private val zerobinClient = RetrofitObject.provideZerobinApi(getJWT())

    private fun getJWT() = pref.getString(MyPageRepository.PREF_JWT, "") ?: ""

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