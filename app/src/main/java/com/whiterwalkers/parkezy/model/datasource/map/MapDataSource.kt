package com.whiterwalkers.parkezy.model.datasource.map

import com.whiterwalkers.parkezy.model.pojos.Car
import com.whiterwalkers.parkezy.model.pojos.Location
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import kotlinx.coroutines.flow.Flow

interface MapDataSource {
    fun getParkingSpots(currentLocation: Location): Flow<List<ParkingSpot>>
    fun getVehicleList(userId: Int): Flow<List<Car>>
}