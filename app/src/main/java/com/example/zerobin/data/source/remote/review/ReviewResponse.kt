package com.example.zerobin.data.source.remote.review

import com.example.zerobin.data.source.remote.shop.ImageResponse

data class ReviewResponse(
    val comment: String?,
    val createdAt: String?,
    val hashtag: List<Hashtag>?,
    val image: List<ImageResponse>?,
    val name: String?,
    val nickName: String?,
    val reviewIndex: Int,
    val stamp: Int?,
) {
    data class Hashtag(
        val name: String?,
    )
}

