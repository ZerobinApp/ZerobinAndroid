package com.example.zerobin.domain.entity

data class User(
    val email: String,
    val nickname: String,
    val favoriteShopCount: Int,
    val favoriteReviewCount: Int,
    val ReviewList: ArrayList<Review>,
    val favoriteShop: List<Shop>
)
