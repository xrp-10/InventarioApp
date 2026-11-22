package com.example.inventarioapp.feature.product.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.inventarioapp.feature.product.presentation.section.ProductFormSection
import com.example.inventarioapp.feature.product.presentation.viewmodel.ProductFormViewModel

/** La Screen orquesta estado, estructura Scaffold y salida del formulario. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormScreen(
    vm: ProductFormViewModel,
    productId: String?,
    onDone: () -> Unit
) {
    val ui by vm.ui.collectAsStateWithLifecycle()
    LaunchedEffect(productId) { vm.load(productId) }
    LaunchedEffect(ui.saved) { if (ui.saved) onDone() }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(if (productId == null) "Nuevo producto" else "Editar producto")
        })
    }) { padding ->
        Box(Modifier.padding(padding)) {
            ProductFormSection(
                ui = ui,
                onName = vm::updateName,
                onDescription = vm::updateDescription,
                onPrice = vm::updatePrice,
                onQuantity = vm::updateQuantity,
                onSave = vm::save
            )
        }
    }
}
