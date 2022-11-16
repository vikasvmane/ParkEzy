package com.whiterwalkers.parkezy.model.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(val lat: Double, val lng: Double) : Parcelable
