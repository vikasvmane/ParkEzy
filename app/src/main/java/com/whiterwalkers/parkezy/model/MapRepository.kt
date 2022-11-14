package com.whiterwalkers.parkezy.model

import com.whiterwalkers.parkezy.model.pojos.Location
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot

interface MapRepository {
    suspend fun getNearByParkingSpots(currentLocation: Location): List<ParkingSpot>
}