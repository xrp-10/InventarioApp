package com.example.inventarioapp.feature.product.presentation.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventarioapp.feature.product.domain.model.Product
import com.example.inventarioapp.feature.product.presentation.composables.ProductCard

/** Bloque funcional de lista: no conoce Room, Firestore ni ViewModel. */
@Composable
fun ProductListSection(
    products: List<Product>,
    onEdit: (String) -> Unit,
    onAskDelete: (String) -> Unit
) {
    if (products.isEmpty()) {
        Text("Sin productos. Pulsa + para comenzar.", Modifier.padding(24.dp))
        return
    }
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products, key = { it.id }) { product ->
            ProductCard(product,
                onEdit = { onEdit(product.id) },
                onAskDelete = { onAskDelete(product.id) }
            )
        }
    }
}
