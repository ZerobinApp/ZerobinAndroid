package com.example.zerobin.domain.entity

data class ShopDetail(
    val name: String,
    val hashTag: String,
    val favorite: Boolean,
    val address: String,
    val imageUrlList: List<String>,
    val featureList: List<Feature>,
    val shopDescription: String,
    val reviewList: ArrayList<Review>,
) {
    data class Feature(
        val featureImageUrl: String,
        val featureName: String,
        val featureDescription: String,
    )

}
