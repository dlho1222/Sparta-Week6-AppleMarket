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
    val like: Int,
    val conversation: Int
) : Parcelable
