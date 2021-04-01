package com.shop.zerobin.data.source.remote.review

import com.shop.zerobin.data.source.remote.shop.ImageResponse

data class ReviewModifyRequest(
    val comment: String,
    val deletehashtag: List<Int>,
    val updatehashtag: List<Int>,
    val deleteimage: List<String>,
    val updateimage: List<String>,
    val stamp:Int
)
