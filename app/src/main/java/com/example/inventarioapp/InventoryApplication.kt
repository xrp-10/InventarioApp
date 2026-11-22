package com.example.inventarioapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Punto de entrada para el grafo de dependencias de Hilt.
@HiltAndroidApp
class InventoryApplication : Application()
