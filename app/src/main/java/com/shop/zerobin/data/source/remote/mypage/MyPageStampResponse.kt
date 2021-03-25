package com.shop.zerobin.data.source.remote.mypage

import com.shop.zerobin.data.source.remote.review.ReviewResponse

data class MyPageStampResponse(
        val result: Result,
        val isSuccess: Boolean,
        val code: Int?,
        val message: String?
) {
    data class Result(
            val review: List<ReviewResponse>
    )
}