package com.whiterwalkers.parkezy.model.repositories.manageVehicles

import com.whiterwalkers.parkezy.model.pojos.Car
import com.whiterwalkers.parkezy.model.pojos.service.CreateParkingResponse

interface IManageVehicleRepository {
    suspend fun getCarList(userId: Int): List<Car>
    suspend fun addCar(car: Car): CreateParkingResponse
    suspend fun saveSelectedCar(car: Car)
}