package com.example.inventarioapp.feature.product.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// La marca pending evita perder cambios hechos sin conexion.
@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val priceCop: Long,
    val quantity: Int,
    val updatedAt: Long,
    val pending: Boolean = true,
    val isDeleted: Boolean = false
)
