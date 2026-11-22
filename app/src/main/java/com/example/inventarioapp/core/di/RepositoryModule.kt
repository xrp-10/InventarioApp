package com.example.inventarioapp.core.di

import com.example.inventarioapp.feature.product.data.repository.ProductRepositoryImpl
import com.example.inventarioapp.feature.product.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton
    abstract fun bindProducts(impl: ProductRepositoryImpl): ProductRepository
}
