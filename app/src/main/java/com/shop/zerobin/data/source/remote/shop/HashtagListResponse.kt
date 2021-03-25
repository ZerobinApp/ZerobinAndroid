package com.shop.zerobin.data.source.remote.shop

data class HashtagListResponse(
        val result: List<HashtagResponse>,
        val isSuccess: Boolean,
        val code: Int,
        val message: String
)