package com.example.inventarioapp.feature.product.data.mapper

import com.example.inventarioapp.feature.product.data.local.ProductEntity
import com.example.inventarioapp.feature.product.data.remote.ProductDto

// Transformamos los registros entre la BD local y la representacion remota.
fun ProductEntity.toDto() = ProductDto(
    id, name, description, priceCop, quantity, updatedAt, isDeleted
)

fun ProductDto.toEntity() = ProductEntity(
    id, name, description, priceCop, quantity, updatedAt,
    pending = false, isDeleted = isDeleted
)
