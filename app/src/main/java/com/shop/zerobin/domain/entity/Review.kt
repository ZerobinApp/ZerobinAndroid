package com.shop.zerobin.domain.entity


data class Review(
    val comment: String,
    val createdAt: String,
    val hashTagList: List<Hashtag>,
    val imageList: List<String>,
    val name: String,
    val nickName: String,
    val reviewIndex: Int,
    val shopIndex: Int,
    val stamp: Boolean,
    val owner: Boolean
)
