package com.example.inventarioapp.feature.product.data.repository

import com.example.inventarioapp.feature.product.data.local.ProductDao
import com.example.inventarioapp.feature.product.data.mapper.toDomain
import com.example.inventarioapp.feature.product.data.mapper.toDto
import com.example.inventarioapp.feature.product.data.mapper.toEntity
import com.example.inventarioapp.feature.product.data.remote.FirestoreProductDataSource
import com.example.inventarioapp.feature.product.domain.model.Product
import com.example.inventarioapp.feature.product.domain.repository.ProductRepository
import com.example.inventarioapp.feature.product.domain.repository.SyncReport
import com.google.firebase.auth.FirebaseAuth
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    // Lazy: Firebase solo se crea al sincronizar. Asi el CRUD local funciona
    // aunque todavia no exista google-services.json (fase B de la guia).
    private val remoteLazy: Lazy<FirestoreProductDataSource>,
    private val authLazy: Lazy<FirebaseAuth>
) : ProductRepository {
    private val syncMutex = Mutex()

    override fun observeProducts(): Flow<List<Product>> =
        dao.observeVisible().map { rows -> rows.map { it.toDomain() } }

    override suspend fun findById(id: String): Product? =
        dao.find(id)?.takeUnless { it.isDeleted }?.toDomain()

    override suspend fun save(product: Product) {
        // Guardamos primero en Room: se mantiene el modo offline.
        dao.upsert(product.copy(updatedAt = System.currentTimeMillis())
            .toEntity())
    }

    override suspend fun delete(id: String) {
        val current = dao.find(id) ?: return
        // Tombstone: no borrar fisicamente antes de sincronizar.
        dao.upsert(current.copy(
            isDeleted = true, pending = true,
            updatedAt = System.currentTimeMillis()
        ))
    }

    override suspend fun syncNow(): SyncReport = syncMutex.withLock {
        val auth = authLazy.get()
        val remote = remoteLazy.get()
        // Si aun no hay sesion, necesita red la primera vez.
        val uid = auth.currentUser?.uid
            ?: auth.signInAnonymously().await().user?.uid
            ?: error("No fue posible iniciar sesion anonima")

        var uploaded = 0
        for (local in dao.getPending()) {
            remote.push(uid, local.toDto())
            dao.markSynced(local.id, local.updatedAt)
            uploaded++
        }

        val cloudItems = remote.fetchFromServer(uid)
        for (dto in cloudItems) {
            val cloud = dto.toEntity()
            val local = dao.find(cloud.id)
            // Nunca sobrescribir una edicion que sigue pendiente.
            if (local?.pending == true) continue
            if (local == null || cloud.updatedAt > local.updatedAt) {
                dao.upsert(cloud)
            }
        }
        SyncReport(uploaded, cloudItems.size)
    }
}
