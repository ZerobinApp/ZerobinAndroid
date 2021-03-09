package com.example.zerobin.data.source.remote.Mypage

import com.example.zerobin.domain.entity.Shop

data class MypageShopResponse(
    val result: Result,
    val isSuccess: Boolean,
    val code: Int?,
    val message: String?
) {
    data class Result(
        val shop: List<Shop>
    )
}