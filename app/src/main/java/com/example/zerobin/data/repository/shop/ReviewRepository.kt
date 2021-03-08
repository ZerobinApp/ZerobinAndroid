package com.example.zerobin.data.repository.shop

import com.example.zerobin.data.source.remote.RetrofitObject
import com.example.zerobin.domain.DataResult
import com.example.zerobin.domain.entity.Review
import com.example.zerobin.domain.mapper.DataToEntityExtension.reviewDataToEntity

class ReviewRepository {

    private val zerobinClient = RetrofitObject.provideZerobinApi()

    suspend fun getReviewList(): DataResult<List<Review>> {


        val response = zerobinClient.getReviewList()
        if(!response.isSuccess){
            DataResult.Error(Exception(response.message))
        }
        return DataResult.Success(response.result.review.map(::reviewDataToEntity))
    }
}