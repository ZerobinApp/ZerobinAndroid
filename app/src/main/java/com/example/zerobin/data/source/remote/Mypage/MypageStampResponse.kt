package com.example.zerobin.data.source.remote.Mypage

import com.example.zerobin.data.source.remote.review.ReviewResponse

data class MypageStampResponse(
    val result: Result?,
    val isSuccess: Boolean?,
    val code: Int?,
    val message: String?
) {
    data class Result(
        val review: List<ReviewResponse>?
    )
}