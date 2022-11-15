package com.whiterwalkers.parkezy.model.datasource.manageparking

import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import kotlinx.coroutines.flow.Flow

interface ManageParkingDataSource {
    fun getParkingSpots(userId: Int): Flow<List<ParkingSpot>>
}