package com.example.zerobin.domain.entity

data class ShopDetail(
    val hashtagList: List<Hashtag>,
    val imageList: List<String>,
    val zzim: Boolean,
    val shopIndex: Int,
    val name: String,
    val location: String,
    val comment: String,
    val reviewList: List<Review>,
) {
    data class Hashtag(
        val name: String,
        val image: String,
        val title: String,
        val comment: String,
    )
}