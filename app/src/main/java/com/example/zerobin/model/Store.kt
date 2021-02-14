package com.example.zerobin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Store(
    val name: String,
    val hashTag: String,
    val favorite: Boolean,
    val address: String,
    val imageUrl: String,
) : Parcelable
