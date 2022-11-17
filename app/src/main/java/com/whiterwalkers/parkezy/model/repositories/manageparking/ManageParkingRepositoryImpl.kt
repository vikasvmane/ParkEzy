package com.whiterwalkers.parkezy.model.repositories.manageparking

import com.whiterwalkers.parkezy.model.datasource.manageparking.ManageParkingDataSource
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import com.whiterwalkers.parkezy.model.pojos.service.CreateParkingResponse
import javax.inject.Inject

class ManageParkingRepositoryImpl @Inject constructor(private val manageParkingDataSource: ManageParkingDataSource) :
    ManageParkingRepository {
    override suspend fun getUserParkingSpots(userId: Int): List<ParkingSpot> {
        val mutableList = mutableListOf<ParkingSpot>()
        manageParkingDataSource.getParkingSpots(userId).collect {
            mutableList.addAll(it)
        }
        return mutableList
    }

    override suspend fun createParkingSpot(parkingSpot: ParkingSpot): CreateParkingResponse {
        return CreateParkingResponse(isSuccess = true)
    }
}