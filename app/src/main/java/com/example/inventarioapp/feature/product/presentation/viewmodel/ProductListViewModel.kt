package com.example.inventarioapp.feature.product.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventarioapp.feature.product.domain.repository.ProductRepository
import com.example.inventarioapp.feature.product.presentation.state.ProductListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _ui = MutableStateFlow(ProductListUiState())
    val ui: StateFlow<ProductListUiState> = _ui

    init {
        viewModelScope.launch {
            repository.observeProducts()
                .catch { e -> _ui.update { it.copy(message = e.message) } }
                .collect { products ->
                    _ui.update { it.copy(products = products) }
                }
        }
        sync()
    }

    fun sync() = viewModelScope.launch {
        _ui.update { it.copy(isSyncing = true, message = null) }
        try {
            val report = repository.syncNow()
            _ui.update {
                it.copy(message = "Sincronizado: ${report.uploaded} enviados, " +
                        "${report.downloaded} en la nube")
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            // El motivo real queda en Logcat (filtro: InventarioSync).
            Log.w("InventarioSync", "Fallo la sincronizacion", e)
            _ui.update { it.copy(message = "Sin conexion: datos locales disponibles") }
        } finally {
            _ui.update { it.copy(isSyncing = false) }
        }
    }

    fun delete(id: String) = viewModelScope.launch {
        repository.delete(id)
        sync()
    }
}
