package com.whiterwalkers.parkezy.model.repositories.manageVehicles

import com.whiterwalkers.parkezy.model.datasource.map.MapDataSource
import com.whiterwalkers.parkezy.model.pojos.Car
import com.whiterwalkers.parkezy.model.pojos.service.CreateParkingResponse
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(private val dataSource: MapDataSource) :
    IManageVehicleRepository {

    override suspend fun getCarList(userId: Int): List<Car> {
        val mutableList = mutableListOf<Car>()
        dataSource.getVehicleList(userId).collect {
            mutableList.addAll(it)
        }
        return mutableList
    }

    override suspend fun addCar(car: Car): CreateParkingResponse {
        return CreateParkingResponse(true)
    }

    override suspend fun saveSelectedCar(car: Car) {
        dataSource.saveVehicle(car)
    }
}