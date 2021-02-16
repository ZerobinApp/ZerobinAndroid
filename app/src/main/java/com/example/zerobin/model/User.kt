package com.example.zerobin.model

data class User(
    val email: String,
    val nickname: String,
    val favoriteStoreCount: Int,
    val favoriteReviewCount: Int,
    val ReviewList: ArrayList<Review>
)
