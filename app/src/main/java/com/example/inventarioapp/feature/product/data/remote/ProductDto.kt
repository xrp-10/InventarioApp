package com.example.inventarioapp.feature.product.data.remote

/** Representacion del documento Firestore; no contiene anotaciones Room. */
data class ProductDto(
    val id: String,
    val name: String,
    val description: String,
    val priceCop: Long,
    val quantity: Int,
    val updatedAt: Long,
    val isDeleted: Boolean
)
