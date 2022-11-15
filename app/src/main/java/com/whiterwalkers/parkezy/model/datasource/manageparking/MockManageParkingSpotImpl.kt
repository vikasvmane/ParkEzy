package com.whiterwalkers.parkezy.model.datasource.manageparking

import com.whiterwalkers.parkezy.model.pojos.Location
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

class MockManageParkingSpotImpl @Inject constructor() : ManageParkingDataSource {
    private fun getParkingSpot(
        name: String,
        address: String, info: String, rating: Float, lat: Double, lng: Double
    ) =
        ParkingSpot(
            parkingId = getRandomId(),
            parkingName = name,
            address = address,
            info = info,
            ratings = rating,
            location = Location(lat, lng)
        )

    private fun getRandomId() = Random.nextInt(0, 100)

    override fun getParkingSpots(userId: Int) = flow {
        emit(
            listOf(
                getParkingSpot(
                    "Theia",
                    "Eela society",
                    "EV Parking | CCTV",
                    3f,
                    18.6269348,
                    73.7345562
                ),
                getParkingSpot(
                    "Dream-It Furniture",
                    "Opp Siddhashila",
                    "Open",
                    2f,
                    18.6260854,
                    73.7339296
                ),
                getParkingSpot("Panjabi Tadka", "Kate Wasti", "Security", 4f, 18.625029, 73.7340166)
            )
        )
    }
}