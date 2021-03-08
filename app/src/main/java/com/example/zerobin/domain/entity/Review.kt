package com.example.zerobin.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val comment: String,
    val createdAt: String,
    val hashtagList: List<String>,
    val imageList: List<String>,
    val name: String,
    val nickName: String,
    val reviewIndex: Int,
    val stamp: Boolean,
) : Parcelable
