package com.whiterwalkers.parkezy.model.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParkingSpot(
    val parkingId: Int,
    val parkingName: String,
    val address: String,
    val info: String,
    val ratings: Float,
    val location: Location
) :
    Parcelable
