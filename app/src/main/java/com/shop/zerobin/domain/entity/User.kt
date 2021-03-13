package com.shop.zerobin.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userIndex: Int,
    val nickname: String,
    val favoriteShopCount: Int,
    val favoriteReviewCount: Int,
): Parcelable
