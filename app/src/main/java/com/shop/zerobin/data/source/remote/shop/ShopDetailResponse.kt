package com.shop.zerobin.data.source.remote.shop

import com.shop.zerobin.data.source.remote.review.ReviewResponse

data class ShopDetailResponse(
    val result: Result,
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
) {
    data class Result(
        val hashtag: List<Hashtag>?,
        val image: List<ImageResponse>?,
        val zzim: Int?,
        val shopIndex: Int,
        val name: String?,
        val location: String?,
        val comment: String?,
        val review: List<ReviewResponse>?,
    ) {
        data class Hashtag(
            val name: String?,
            val image: String?,
            val title: String?,
            val comment: String?,
        )
    }
}