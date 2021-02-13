package com.example.zerobin.model

data class Review(
    val name: String,
    val hashTag: String,
    val favorite: Boolean,
    val date: String,
    val review: String,
    val imageUrl: String,
)
