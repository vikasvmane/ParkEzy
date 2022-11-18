package com.whiterwalkers.parkezy.di


import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.whiterwalkers.parkezy.model.datasource.history.HistoryDataStore
import com.whiterwalkers.parkezy.model.datasource.history.MockHistoryDataStoreImpl
import com.whiterwalkers.parkezy.model.datasource.manageparking.ManageParkingDataSource
import com.whiterwalkers.parkezy.model.datasource.manageparking.MockManageParkingSpotImpl
import com.whiterwalkers.parkezy.model.datasource.map.MapDataSource
import com.whiterwalkers.parkezy.model.datasource.map.MockMapDataSourceImpl
import com.whiterwalkers.parkezy.model.datasource.payments.MockPaymentDataSourceImpl
import com.whiterwalkers.parkezy.model.datasource.payments.PaymentsDataSource
import com.whiterwalkers.parkezy.model.repositories.history.HistoryRepository
import com.whiterwalkers.parkezy.model.repositories.history.HistoryRepositoryImpl
import com.whiterwalkers.parkezy.model.repositories.manageVehicles.IManageVehicleRepository
import com.whiterwalkers.parkezy.model.repositories.manageVehicles.VehicleRepositoryImpl
import com.whiterwalkers.parkezy.model.repositories.manageparking.ManageParkingRepository
import com.whiterwalkers.parkezy.model.repositories.manageparking.ManageParkingRepositoryImpl
import com.whiterwalkers.parkezy.model.repositories.map.MapRepository
import com.whiterwalkers.parkezy.model.repositories.map.MapRepositoryImpl
import com.whiterwalkers.parkezy.model.repositories.payment.PaymentRepository
import com.whiterwalkers.parkezy.model.repositories.payment.PaymentRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.prefs.Preferences

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun getMapDataSource(impl: MockMapDataSourceImpl): MapDataSource = impl

    @Provides
    fun getMapRepository(impl: MapRepositoryImpl): MapRepository = impl

    @Provides
    fun getManageParkingRepository(impl: ManageParkingRepositoryImpl): ManageParkingRepository =
        impl

    @Provides
    fun getManageParkingDataSource(impl: MockManageParkingSpotImpl): ManageParkingDataSource =
        impl

    @Provides
    fun getVehicleRepository(impl: VehicleRepositoryImpl): IManageVehicleRepository = impl

    @Provides
    fun getParkingHistoryRepository(impl: HistoryRepositoryImpl): HistoryRepository =
        impl

    @Provides
    fun getParkingHistoryDataSource(impl: MockHistoryDataStoreImpl): HistoryDataStore =
        impl

    @Provides
    fun getPaymentRepository(impl: PaymentRepositoryImpl): PaymentRepository =
        impl

    @Provides
    fun getPaymentDataSource(impl: MockPaymentDataSourceImpl): PaymentsDataSource =
        impl
}