package com.whiterwalkers.parkezy.model.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
/**
 * Manages schedule for parking spots.
 * @param startHour start timing for the day 08:00
 * @param endHour end timing for the day 20:00
 * @param weekdays week days for which the parking is available [Monday, Wednesday, Friday]
 */
@Parcelize
data class SpotSchedule(val startHour: String, val endHour: String, val weekdays: List<String>? = null):
    Parcelable
