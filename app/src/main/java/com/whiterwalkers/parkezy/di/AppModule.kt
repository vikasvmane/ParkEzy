package com.whiterwalkers.parkezy.di

import com.whiterwalkers.parkezy.model.MapRepository
import com.whiterwalkers.parkezy.model.MapRepositoryImpl
import com.whiterwalkers.parkezy.model.datasource.MapDataSource
import com.whiterwalkers.parkezy.model.datasource.MockDataSourceImpl
import com.whiterwalkers.parkezy.model.datasource.manageparking.ManageParkingDataSource
import com.whiterwalkers.parkezy.model.datasource.manageparking.MockManageParkingSpotImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun getMockMapDataSource(impl: MockDataSourceImpl): MapDataSource = impl

    @Provides
    fun getMapRepository(impl: MapRepositoryImpl): MapRepository = impl

    @Provides
    fun getMockManageParkingDataSource(impl: MockManageParkingSpotImpl): ManageParkingDataSource = impl
}