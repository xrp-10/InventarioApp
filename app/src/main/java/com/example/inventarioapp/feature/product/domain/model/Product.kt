package com.example.inventarioapp.feature.product.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val priceCop: Long,
    val quantity: Int,
    val updatedAt: Long = System.currentTimeMillis()
)
