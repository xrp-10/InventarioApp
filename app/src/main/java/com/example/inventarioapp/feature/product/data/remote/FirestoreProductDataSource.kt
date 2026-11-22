package com.example.inventarioapp.feature.product.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/** Unica clase que conoce las rutas de la coleccion de Firestore. */
class FirestoreProductDataSource @Inject constructor(
    private val db: FirebaseFirestore
) {
    private fun products(uid: String) = db.collection("users")
        .document(uid).collection("products")

    suspend fun push(uid: String, dto: ProductDto) {
        // El ID del documento es el ID estable del producto local.
        val data = mapOf(
            "id" to dto.id, "name" to dto.name,
            "description" to dto.description,
            "priceCop" to dto.priceCop,
            "quantity" to dto.quantity,
            "updatedAt" to dto.updatedAt,
            "isDeleted" to dto.isDeleted
        )
        products(uid).document(dto.id).set(data).await()
    }

    suspend fun fetchFromServer(uid: String): List<ProductDto> =
        products(uid).get(Source.SERVER).await().documents.mapNotNull { doc ->
            val id = doc.getString("id") ?: return@mapNotNull null
            ProductDto(
                id = id,
                name = doc.getString("name").orEmpty(),
                description = doc.getString("description").orEmpty(),
                priceCop = doc.getLong("priceCop") ?: 0L,
                quantity = (doc.getLong("quantity") ?: 0).toInt(),
                updatedAt = doc.getLong("updatedAt") ?: 0L,
                isDeleted = doc.getBoolean("isDeleted") ?: false
            )
        }
}
