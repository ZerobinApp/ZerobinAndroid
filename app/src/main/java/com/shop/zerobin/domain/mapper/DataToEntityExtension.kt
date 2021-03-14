package com.shop.zerobin.domain.mapper

import com.shop.zerobin.data.source.remote.mypage.MyPageShopResponse
import com.shop.zerobin.data.source.remote.mypage.UserResponse
import com.shop.zerobin.data.source.remote.review.ReviewResponse
import com.shop.zerobin.data.source.remote.shop.ImageResponse
import com.shop.zerobin.data.source.remote.shop.ShopDetailResponse
import com.shop.zerobin.data.source.remote.shop.ShopListResponse
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.domain.entity.Shop
import com.shop.zerobin.domain.entity.ShopDetail
import com.shop.zerobin.domain.entity.User

object DataToEntityExtension {
    fun hashtagDataToEntity(hashtag: ShopListResponse.Result.Hashtag) = hashtag.name ?: ""

    fun shopDataToEntity(shop: ShopListResponse.Result.Shop) =
        Shop(
            shopIndex = shop.shopIndex,
            name = shop.name ?: "",
            location = shop.location ?: "",
            imageList = shop.image?.map(::imageDataToEntity) ?: emptyList(),
            zzim = shop.zzim == 1,
        )

    fun myPageShopDataToEntity(shop: MyPageShopResponse.Result.Shop) =
        Shop(
            shopIndex = shop.shopIndex,
            name = shop.name ?: "",
            location = shop.location ?: "",
            imageList = shop.image?.map(::imageDataToEntity) ?: emptyList(),
            zzim = shop.zzim == 1,
        )

    fun reviewDataToEntity(review: ReviewResponse) =
        Review(
            comment = review.comment ?: "",
            createdAt = review.createdAt ?: "",
            hashtagList = review.hashtag?.map { it.name ?: "" } ?: emptyList(),
            imageList = review.image?.map(::imageDataToEntity) ?: emptyList(),
            name = review.name ?: "",
            nickName = review.nickName ?: "",
            stamp = review.stamp == 1,
            reviewIndex = review.reviewIndex
        )

    private fun imageDataToEntity(image: ImageResponse) = image.pictureUrl ?: ""

    fun ShopDetailResponse.Result.map() = ShopDetail(
        hashtagList = this.hashtag?.map {
            ShopDetail.Hashtag(
                it.name ?: "",
                it.image ?: "",
                it.title ?: "",
                it.comment ?: ""
            )
        } ?: emptyList(),
        imageList = this.image?.map(::imageDataToEntity) ?: emptyList(),
        zzim = this.zzim == 1,
        shopIndex = this.shopIndex,
        name = this.name ?: "",
        location = this.location ?: "",
        comment = this.comment ?: "",
        reviewList = this.review?.map {
            Review(
                reviewIndex = it.reviewIndex,
                nickName = it.nickName ?: "",
                comment = it.comment ?: "",
                createdAt = it.createdAt ?: "",
                name = it.name ?: "",
                imageList = it.image?.map { it.pictureUrl ?: "" } ?: emptyList(),
                stamp = it.stamp == 1,
                hashtagList = it.hashtag?.map { it.name ?: "" } ?: emptyList(),
            )
        } ?: emptyList()
    )

    fun UserResponse.Result.map() = User(
        userIndex = this.userIndex ?: 0,
        nickname = this.nickName ?: "",
        favoriteShopCount = this.zzimnum ?: 0,
        favoriteReviewCount = this.reviewnum ?: 0
    )
}
