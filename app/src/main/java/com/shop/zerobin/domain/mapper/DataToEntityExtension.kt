package com.shop.zerobin.domain.mapper

import com.shop.zerobin.data.source.remote.mypage.MyPageShopResponse
import com.shop.zerobin.data.source.remote.mypage.UserResponse
import com.shop.zerobin.data.source.remote.review.ReviewDetailResponse
import com.shop.zerobin.data.source.remote.review.ReviewResponse
import com.shop.zerobin.data.source.remote.shop.HashtagResponse
import com.shop.zerobin.data.source.remote.shop.ImageResponse
import com.shop.zerobin.data.source.remote.shop.ShopDetailResponse
import com.shop.zerobin.data.source.remote.shop.ShopListResponse
import com.shop.zerobin.domain.entity.*

object DataToEntityExtension {
    fun hashtagDataToEntity(hashtag: ShopListResponse.Result.Hashtag) = hashtag.name ?: ""

    fun hashtagListDataToEntity(hashtagResponse: HashtagResponse) =
        Hashtag(hashtagResponse.hashtagIndex, hashtagResponse.name)

    fun shopDataToEntity(shop: ShopListResponse.Result.Shop) =
        Shop(
            shopIndex = shop.shopIndex,
            name = shop.name ?: "",
            location = shop.location ?: "",
            imageUrl = shop.image?.map(::imageDataToEntity)?.get(0) ?: "",
            zzim = shop.zzim == 1,
        )


    fun myPageShopDataToEntity(shop: MyPageShopResponse.Result.Shop) =
        Shop(
            shopIndex = shop.shopIndex,
            name = shop.name ?: "",
            location = shop.location ?: "",
            imageUrl = shop.image?.map(::imageDataToEntity)?.get(0) ?: "",
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
            owner = review.owner == 1,
            reviewIndex = review.reviewIndex,
            shopIndex = review.shopIndex
        )

    private fun imageDataToEntity(image: ImageResponse) = image.pictureUrl ?: ""

    fun ReviewDetailResponse.Result.map() =Review(
        comment = this.comment ?: "",
        createdAt = this.createdAt ?: "",
        hashtagList = this.hashtag?.map {
                it.name ?: ""

        } ?: emptyList(),
        imageList = this.image?.map(::imageDataToEntity) ?: emptyList(),
        name = this.name ?: "",
        nickName = this.nickName ?: "",
        stamp = this.stamp == 1,
        owner = this.owner == 1,
        reviewIndex = this.reviewIndex,
        shopIndex = this.shopIndex

    )

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
                shopIndex = it.shopIndex,
                nickName = it.nickName ?: "",
                comment = it.comment ?: "",
                createdAt = it.createdAt ?: "",
                name = it.name ?: "",
                imageList = it.image?.map { it.pictureUrl ?: "" } ?: emptyList(),
                stamp = it.stamp == 1,
                hashtagList = it.hashtag?.map { it.name ?: "" } ?: emptyList(),
                owner = it.owner == 1
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
