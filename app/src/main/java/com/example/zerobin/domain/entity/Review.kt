package com.example.zerobin.domain.entity

data class Review(
    val name: String,
    val hashTag: String,
    val favorite: Boolean,
    val date: String,
    val review: String,
    val imageUrl: String,
)
