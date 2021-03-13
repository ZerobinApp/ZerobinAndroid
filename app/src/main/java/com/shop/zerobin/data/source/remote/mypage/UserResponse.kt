package com.shop.zerobin.data.source.remote.mypage

data class UserResponse(
    val result: Result,
    val isSuccess: Boolean,
    val code: Int?,
    val message: String?
) {
    data class Result(
        val userIndex: Int?,
        val nickName: String?,
        val zzimnum: Int?,
        val reviewnum: Int?
    )
}