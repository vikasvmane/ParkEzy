package com.whiterwalkers.parkezy.model.datasource.map

import android.content.Context
import com.whiterwalkers.parkezy.model.pojos.*
import com.whiterwalkers.parkezy.ui.utils.DataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Provides dummy data to the app. To be replaced with RemoteDataSource
 */
class MockMapDataSourceImpl @Inject constructor(@ApplicationContext val appContext: Context) :
    MapDataSource {
    // private val Context.dataStore by preferencesDataStore("user_preferences")
    private fun getParkingSpot(
        id: Int,
        name: String,
        address: String, info: String, rating: Float, lat: Double, lng: Double
    ) =
        ParkingSpot(
            parkingId = id,
            parkingName = name,
            address = address,
            info = info,
            ratings = rating,
            location = Location(lat, lng),
            size = Size.SEDAN.name,
            rate = Rate(standardRate = 30.0),
            spotSchedule = null,
            isAvailable = true,
            isEvEnabled = false
        )

    override fun getParkingSpots(currentLocation: Location) = flow {
        emit(
            listOf(
                getParkingSpot(
                    123,
                    "Theia",
                    "Eela society",
                    "EV Parking | CCTV",
                    3f,
                    18.352092980717263, 74.03363915610463
                ),
                getParkingSpot(
                    234,
                    "Dream-It Furniture",
                    "Opp Siddhashila",
                    "Open",
                    2f,
                    18.353467702254438, 74.03282376460234
                ),
                getParkingSpot(
                    674,
                    "Panjabi Tadka",
                    "Kate Wasti",
                    "Security",
                    4f,
                    18.354323079021654, 74.03388591932243
                )
            )
        )
    }

    override fun getVehicleList(userId: Int) = flow {
        emit(
            listOf(
                Car(
                    1,
                    "TATA",
                    "Nexon XZ",
                    "My car",
                    123121312,
                    Size.SUV,
                    checkIsPrimary(1)
                ),
                Car(
                    2,
                    "Hyundai",
                    "Niox i10 Sports",
                    "My wife's car",
                    45645645645,
                    Size.SUV,
                    checkIsPrimary(1)
                ),
                Car(
                    3,
                    "TATA",
                    "Safari",
                    "Dad's car",
                    123121312,
                    Size.HATCHBACK,
                    checkIsPrimary(3)
                ),
            )
        )
    }

    override suspend fun saveVehicle(car: Car) {
//        val dataStore = appContext.dataStore
//        dataStore.edit {
//            it[stringPreferencesKey(USER_SELECTED_CAR)] = Gson().toJson(car)
//        }
        DataStore.saveSelectedCar(car)
    }

    private fun checkIsPrimary(id: Int) =
        DataStore.getSelectedCar() != null && DataStore.getSelectedCar()?.vehicleId == id
}