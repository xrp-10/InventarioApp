package com.example.inventarioapp.feature.product.presentation.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.inventarioapp.feature.product.presentation.composables.ProductFormField
import com.example.inventarioapp.feature.product.presentation.state.ProductFormUiState

/** Seccion de formulario: solo recibe UiState y callbacks. */
@Composable
fun ProductFormSection(
    ui: ProductFormUiState,
    onName: (String) -> Unit,
    onDescription: (String) -> Unit,
    onPrice: (String) -> Unit,
    onQuantity: (String) -> Unit,
    onSave: () -> Unit
) {
    Column(
        Modifier.fillMaxSize().imePadding().padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProductFormField("Nombre", ui.name, onName)
        ProductFormField("Descripcion", ui.description, onDescription)
        ProductFormField("Precio COP", ui.price, onPrice, KeyboardType.Number)
        ProductFormField("Cantidad", ui.quantity, onQuantity, KeyboardType.Number)
        ui.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Button(
            onClick = onSave,
            enabled = !ui.saving,
            modifier = Modifier.fillMaxWidth()
        ) { Text(if (ui.saving) "Guardando..." else "Guardar") }
    }
}
