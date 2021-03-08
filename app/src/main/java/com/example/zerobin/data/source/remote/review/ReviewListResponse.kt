package com.example.zerobin.data.source.remote.review


data class ReviewListResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result,
) {
    data class Result(
        val review: List<ReviewResponse>,
    )
}