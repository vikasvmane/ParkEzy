package com.whiterwalkers.parkezy.model.repositories.map

import com.whiterwalkers.parkezy.model.datasource.map.MapDataSource
import com.whiterwalkers.parkezy.model.pojos.Location
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(private val dataSource: MapDataSource) :
    MapRepository {
    override suspend fun getNearByParkingSpots(currentLocation: Location): List<ParkingSpot> {
        val mutableList = mutableListOf<ParkingSpot>()
        dataSource.getParkingSpots(currentLocation).collect {
            mutableList.addAll(it)
        }
        return mutableList
    }
}