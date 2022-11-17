package com.whiterwalkers.parkezy.model.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParkingSpot(
    val parkingId: Int?,
    val parkingName: String,
    val address: String,
    val info: String,
    val ratings: Float? = null,
    val location: Location?,
    val size: String,
    val rate: Rate?,
    val spotSchedule: SpotSchedule?,
    val isAvailable: Boolean?,
    val isEvEnabled: Boolean
) :
    Parcelable

enum class Size {
    SEDAN, HATCHBACK, SUV
}