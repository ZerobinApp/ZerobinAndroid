package com.example.zerobin.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shop(
    val shopIndex: Int,
    val name: String,
    val location: String,
    val imageList: List<String>,
    val zzim: Boolean,
) : Parcelable