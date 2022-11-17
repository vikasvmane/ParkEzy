package com.whiterwalkers.parkezy.model.repositories.map

import com.whiterwalkers.parkezy.model.pojos.Location
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot

interface MapRepository {
    suspend fun getNearByParkingSpots(currentLocation: Location): List<ParkingSpot>
}