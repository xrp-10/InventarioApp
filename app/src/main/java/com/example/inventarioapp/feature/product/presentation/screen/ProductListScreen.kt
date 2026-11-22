package com.example.inventarioapp.feature.product.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.inventarioapp.feature.product.presentation.composables.ProductDeleteDialog
import com.example.inventarioapp.feature.product.presentation.section.ProductListSection
import com.example.inventarioapp.feature.product.presentation.viewmodel.ProductListViewModel

/** Pantalla contenedora: conecta ViewModel, secciones y navegacion. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    vm: ProductListViewModel,
    onCreate: () -> Unit,
    onEdit: (String) -> Unit
) {
    val ui by vm.ui.collectAsStateWithLifecycle()
    var pendingDeleteId by rememberSaveable { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventario") },
                actions = {
                    TextButton(onClick = { vm.sync() }, enabled = !ui.isSyncing) {
                        Text("Sincronizar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreate) { Text("+") }
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            if (ui.isSyncing) LinearProgressIndicator(Modifier.fillMaxWidth())
            ui.message?.let { Text(it, Modifier.padding(12.dp)) }
            ProductListSection(
                products = ui.products,
                onEdit = onEdit,
                onAskDelete = { pendingDeleteId = it }
            )
        }
    }

    if (pendingDeleteId != null) ProductDeleteDialog(
        onConfirm = {
            pendingDeleteId?.let(vm::delete)
            pendingDeleteId = null
        },
        onDismiss = { pendingDeleteId = null }
    )
}
