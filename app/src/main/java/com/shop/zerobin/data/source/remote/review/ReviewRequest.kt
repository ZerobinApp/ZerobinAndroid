package com.shop.zerobin.data.source.remote.review

data class ReviewRequest(
        val image: List<String>,
        val comment: String,
        val hashtag: List<Int>,
)
