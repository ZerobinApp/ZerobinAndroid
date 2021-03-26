package com.shop.zerobin.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shop(
    val shopIndex: Int,
    val name: String,
    val location: String,
    val imageUrl: String,
    var zzim: Boolean,
) : Parcelable