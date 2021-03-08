package com.example.zerobin.data.source.remote.review

data class ReviewResponse(
    val comment: String,
    val createdAt: String,
    val hashtag: List<Hashtag>,
    val image: List<Image>,
    val name: String,
    val nickName: String,
    val reviewIndex: Int,
    val stamp: Int
){
    data class Hashtag(
        val name : String
    )
    data class Image(
        val pictureUrl:String
    )
}

