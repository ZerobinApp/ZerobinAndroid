package com.example.zerobin.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val email: Int,
    val nickname: String,
    val favoriteShopCount: Int,
    val favoriteReviewCount: Int,
): Parcelable
