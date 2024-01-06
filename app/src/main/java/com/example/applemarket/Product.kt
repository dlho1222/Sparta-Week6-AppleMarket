package com.example.applemarket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val position: Int,
    val imageFile: Int,
    val title: String,
    val contents: String,
    val seller: String,
    val price: Int,
    val location: String,
    val likeCount: Int,
    val conversationCount: Int,
    var isLiked : Boolean = false,
) : Parcelable
