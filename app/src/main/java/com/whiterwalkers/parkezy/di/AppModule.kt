package com.whiterwalkers.parkezy.di


import com.whiterwalkers.parkezy.model.repositories.manageVehicles.IManageVehicleRepository
import com.whiterwalkers.parkezy.model.repositories.manageVehicles.VehicleRepositoryImpl
import com.whiterwalkers.parkezy.model.datasource.manageparking.ManageParkingDataSource
import com.whiterwalkers.parkezy.model.datasource.manageparking.MockManageParkingSpotImpl
import com.whiterwalkers.parkezy.model.datasource.map.MapDataSource
import com.whiterwalkers.parkezy.model.datasource.map.MockMapDataSourceImpl
import com.whiterwalkers.parkezy.model.repositories.manageparking.ManageParkingRepository
import com.whiterwalkers.parkezy.model.repositories.manageparking.ManageParkingRepositoryImpl
import com.whiterwalkers.parkezy.model.repositories.map.MapRepository
import com.whiterwalkers.parkezy.model.repositories.map.MapRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun getMockMapDataSource(impl: MockMapDataSourceImpl): MapDataSource = impl

    @Provides
    fun getMapRepository(impl: MapRepositoryImpl): MapRepository = impl

    @Provides
    fun getManageParkingRepository(impl: ManageParkingRepositoryImpl): ManageParkingRepository =
        impl

    @Provides
    fun getMockManageParkingDataSource(impl: MockManageParkingSpotImpl): ManageParkingDataSource =
        impl

    @Provides
    fun getIMapRepository(impl: VehicleRepositoryImpl): IManageVehicleRepository = impl
}