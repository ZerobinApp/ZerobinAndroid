package com.example.zerobin.domain.mapper

import com.example.zerobin.data.source.remote.shop.ShopListResult
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
}