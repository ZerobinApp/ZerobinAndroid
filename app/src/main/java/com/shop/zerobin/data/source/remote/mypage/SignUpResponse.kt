package com.shop.zerobin.data.source.remote.mypage

data class SignUpResponse(
        val result: Result?,
        val isSuccess: Boolean?,
        val code: Int?,
        val message: String?,
) {
    data class Result(
            val jwt: String?,
    )
}