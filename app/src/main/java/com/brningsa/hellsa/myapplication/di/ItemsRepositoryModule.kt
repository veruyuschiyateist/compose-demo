package com.brningsa.hellsa.myapplication.di

import com.brningsa.hellsa.myapplication.ItemsRepository
import com.brningsa.hellsa.myapplication.ItemsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ItemsRepositoryModule {

    @Binds
    fun itemsRepository(impl: ItemsRepositoryImpl): ItemsRepository
}