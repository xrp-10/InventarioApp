package com.example.inventarioapp.feature.product.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventarioapp.feature.product.domain.model.Product
import java.text.NumberFormat
import java.util.Locale

/** Componente visual reutilizable: recibe datos y emite eventos. */
@Composable
fun ProductCard(
    product: Product,
    onEdit: () -> Unit,
    onAskDelete: () -> Unit
) {
    val format = remember {
        NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO")).apply {
            maximumFractionDigits = 0
        }
    }
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(product.name, style = MaterialTheme.typography.titleMedium)
            if (product.description.isNotBlank()) {
                Text(product.description, style = MaterialTheme.typography.bodySmall)
            }
            Text("${format.format(product.priceCop)} | Cantidad: ${product.quantity}")
            Row {
                TextButton(onClick = onEdit) { Text("Editar") }
                TextButton(onClick = onAskDelete) { Text("Eliminar") }
            }
        }
    }
}
