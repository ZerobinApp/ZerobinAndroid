package com.example.zerobin.data.source.remote.shop


data class ShopListResult(
    val result: Result,
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
) {
    data class Result(
        val hashtag: List<Hashtag>,
        val shop: List<Shop>,
    ) {
        data class Hashtag(
            val name: String?,
        )

        data class Shop(
            val shopIndex: Int,
            val name: String?,
            val location: String?,
            val image: List<Image>?,
            val zzim: Int?,
        ) {
            data class Image(
                val pictureUrl: String?,
            )
        }
    }
}