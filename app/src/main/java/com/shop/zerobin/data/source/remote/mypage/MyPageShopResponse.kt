package com.shop.zerobin.data.source.remote.mypage

import com.shop.zerobin.data.source.remote.shop.ImageResponse

data class MyPageShopResponse(
        val result: Result,
        val isSuccess: Boolean,
        val code: Int,
        val message: String
) {
    data class Result(
            val shop: List<Shop>
    ) {
        data class Shop(
                val shopIndex: Int,
                val name: String?,
                val location: String?,
                val image: List<ImageResponse>?,
                val zzim: Int?
        )
    }
}