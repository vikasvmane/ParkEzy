package com.whiterwalkers.parkezy.model.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Manages rates for parking spot
 * @param standardRate fixed hourly rate e.g 40
 * @param progressiveRate next hour rate in case of more than one hour booking e.g 30
 * @param scheduleRate for bulk advance bookings e.g 20
 */
@Parcelize
data class Rate(val standardRate: Double, val progressiveRate: Double? = null, val scheduleRate: Double? = null) :
    Parcelable
