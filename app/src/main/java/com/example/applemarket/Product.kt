package com.example.applemarket

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
)
