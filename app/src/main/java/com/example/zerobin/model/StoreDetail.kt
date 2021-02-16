package com.example.zerobin.model

data class StoreDetail(
    val name: String,
    val hashTag: String,
    val favorite: Boolean,
    val address: String,
    val imageUrlList: List<String>,
    val featureList: List<Feature>,
    val storeDescription: String,
    val reviewList: List<Review>,
) {
    data class Feature(
        val featureImageUrl: String,
        val featureName: String,
        val featureDescription: String,
    )

}
