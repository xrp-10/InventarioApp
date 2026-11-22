package com.example.inventarioapp.feature.product.data.mapper

import com.example.inventarioapp.feature.product.data.local.ProductEntity
import com.example.inventarioapp.feature.product.domain.model.Product

// Los modelos del dominio nunca exponen detalles de Room.
fun ProductEntity.toDomain() = Product(
    id, name, description, priceCop, quantity, updatedAt
)

fun Product.toEntity() = ProductEntity(
    id = id, name = name, description = description,
    priceCop = priceCop, quantity = quantity,
    updatedAt = updatedAt, pending = true, isDeleted = false
)
