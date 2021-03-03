package com.example.zerobin.domain.mapper

import com.example.zerobin.data.source.remote.review.ReviewListResponse
import com.example.zerobin.data.source.remote.review.ReviewResponse
import com.example.zerobin.data.source.remote.shop.ShopListResult
import com.example.zerobin.domain.entity.Review
import com.example.zerobin.domain.entity.Shop

object DataToEntityExtension {
    fun hashtagDataToEntity(hashtag: ShopListResult.Result.Hashtag) = hashtag.name ?: ""

    fun shopDataToEntity(shop: ShopListResult.Result.Shop) =
        Shop(
            shopIndex = shop.shopIndex,
            name = shop.name ?: "",
            location = shop.location ?: "",
            image = shop.image?.map { it.pictureUrl ?: "" } ?: emptyList(),
            zzim = shop.zzim == 1,
        )

    fun reviewDataToEntity(review: ReviewResponse) =

        Review(
            comment = review.comment,
            createdAt = review.createdAt,
            hashtag = review.hashtag?.map { it.name ?: "" } ?: emptyList(),
            image = review.image?.map { it.pictureUrl ?: "" } ?: emptyList(),
            name = review.name,
            nickName = review.nickName,
            stamp = review.stamp==1,
            reviewIndex = review.reviewIndex
        )


}