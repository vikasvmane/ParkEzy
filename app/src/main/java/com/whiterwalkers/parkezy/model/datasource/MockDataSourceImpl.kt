package com.whiterwalkers.parkezy.model.datasource

import com.whiterwalkers.parkezy.model.pojos.Location
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

/**
 * Provides dummy data to the app. To be replaced with RemoteDataSource
 */
class MockDataSourceImpl @Inject constructor() : MapDataSource {
    private fun getParkingSpot(name: String, lat: Double, lng: Double) =
        ParkingSpot(parkingId = getRandomId(), parkingName = name, Location(lat, lng))

    private fun getRandomId() = Random.nextInt(0, 100)

    override fun getParkingSpots(currentLocation: Location) = flow {
        emit(
            listOf(
                getParkingSpot("Theia", 18.6269348, 73.7345562),
                getParkingSpot("Dream-It Furniture", 18.6260854, 73.7339296),
                getParkingSpot("Panjabi Tadka", 18.625029, 73.7340166)
            )
        )
    }
}