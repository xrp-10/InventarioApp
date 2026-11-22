package com.example.inventarioapp.core.di

import android.content.Context
import androidx.room.Room
import com.example.inventarioapp.core.database.AppDatabase
import com.example.inventarioapp.feature.product.data.local.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides @Singleton
    fun database(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java,
            "inventory.db").build()

    @Provides
    fun productDao(database: AppDatabase): ProductDao = database.productDao()
}
