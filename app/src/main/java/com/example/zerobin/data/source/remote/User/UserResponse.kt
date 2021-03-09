package com.example.zerobin.data.source.remote.User

data class UserResponse(
    val result: List<Result>,
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