package com.whiterwalkers.parkezy.model.repositories.manageparking

import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import com.whiterwalkers.parkezy.model.pojos.service.CreateParkingResponse

interface ManageParkingRepository {
    suspend fun getUserParkingSpots(userId: Int): List<ParkingSpot>
    suspend fun createParkingSpot(parkingSpot: ParkingSpot): CreateParkingResponse
}