package com.example.zerobin.domain.entity

data class User(
    val email: String,
    val nickname: String,
    val favoriteShopCount: Int,
    val favoriteReviewCount: Int,
    val reviewList: List<Review>,
    val favoriteShopList: List<Shop>,
)
