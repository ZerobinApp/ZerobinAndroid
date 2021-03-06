package com.shop.zerobin.data.source.remote.review

import com.shop.zerobin.data.source.remote.shop.ImageResponse

data class ReviewResponse(
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
        val hashtagIndex: Int?,
        val name: String?,
    )
}

