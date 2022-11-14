package com.whiterwalkers.parkezy.di

import com.whiterwalkers.parkezy.model.MapRepository
import com.whiterwalkers.parkezy.model.MapRepositoryImpl
import com.whiterwalkers.parkezy.model.datasource.MapDataSource
import com.whiterwalkers.parkezy.model.datasource.MockDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun getLocalDataSource(impl: MockDataSourceImpl): MapDataSource = impl

    @Provides
    fun getMapRepository(impl: MapRepositoryImpl): MapRepository = impl
}