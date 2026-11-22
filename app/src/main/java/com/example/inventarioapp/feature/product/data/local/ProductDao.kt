package com.example.inventarioapp.feature.product.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    // Room emite nuevos resultados cuando cambia la tabla.
    @Query("SELECT * FROM products WHERE isDeleted = 0 " +
            "ORDER BY updatedAt DESC")
    fun observeVisible(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    suspend fun find(id: String): ProductEntity?

    @Query("SELECT * FROM products WHERE pending = 1")
    suspend fun getPending(): List<ProductEntity>

    @Upsert
    suspend fun upsert(product: ProductEntity)

    // Solo marca sincronizada una version que no haya cambiado.
    @Query("UPDATE products SET pending = 0 " +
            "WHERE id = :id AND updatedAt = :version")
    suspend fun markSynced(id: String, version: Long)
}
