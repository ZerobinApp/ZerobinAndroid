package com.example.zerobin.data.repository.shop

import com.example.zerobin.data.source.remote.RetrofitObject
import com.example.zerobin.domain.entity.Review
import com.example.zerobin.domain.mapper.DataToEntityExtension.reviewDataToEntity

class ReviewRepository {

    private val zerobinClient = RetrofitObject.provideZerobinApi()

    suspend fun getReviewList(): List<Review> {
        val response = zerobinClient.getReviewList()

        return response.result.review.map(::reviewDataToEntity)
    }
}