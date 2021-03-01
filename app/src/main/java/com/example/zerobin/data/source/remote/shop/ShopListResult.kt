package com.example.zerobin.data.source.remote.shop

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


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

        @Parcelize
        data class Shop(
            val shopIndex: Int,
            val name: String?,
            val location: String?,
            val image: List<Image>?,
            val zzim: Int?,
        ) : Parcelable {
            @Parcelize
            data class Image(
                val pictureUrl: String?,
            ) : Parcelable
        }
    }
}