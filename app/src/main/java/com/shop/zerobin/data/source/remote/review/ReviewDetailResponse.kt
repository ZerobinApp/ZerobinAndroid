package com.shop.zerobin.data.source.remote.review

import com.shop.zerobin.data.source.remote.shop.ImageResponse

data class ReviewDetailResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result

){
    data class Result(
        val comment: String?,
        val createdAt: String?,
        val hashtag: List<Hashtag>?,
        val image: List<ImageResponse>?,
        val name: String?,
        val nickName: String?,
        val reviewIndex: Int,
        val shopIndex: Int,
        val stamp: Int?,
        val owner: Int?,
    ) {
        data class Hashtag(
            val name: String?,
        )
    }
}