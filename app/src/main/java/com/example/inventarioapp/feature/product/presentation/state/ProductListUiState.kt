package com.example.inventarioapp.feature.product.presentation.state

import com.example.inventarioapp.feature.product.domain.model.Product

/** Datos que ProductListScreen necesita renderizar. */
data class ProductListUiState(
    val products: List<Product> = emptyList(),
    val isSyncing: Boolean = false,
    val message: String? = null
)
