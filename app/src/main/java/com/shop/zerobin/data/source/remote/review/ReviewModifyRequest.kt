package com.shop.zerobin.data.source.remote.review

data class ReviewModifyRequest(
    val comment: String,
    val deletedhashtag: List<Int>,
    val updatedhashtag: List<Int>,
    val deletedimage: List<String>,
    val updatedimage: List<String>,
    val stamp:Int
)
