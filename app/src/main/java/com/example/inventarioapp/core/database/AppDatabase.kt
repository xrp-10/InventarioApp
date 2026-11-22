package com.example.inventarioapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.inventarioapp.feature.product.data.local.ProductDao
import com.example.inventarioapp.feature.product.data.local.ProductEntity

@Database(entities = [ProductEntity::class], version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
