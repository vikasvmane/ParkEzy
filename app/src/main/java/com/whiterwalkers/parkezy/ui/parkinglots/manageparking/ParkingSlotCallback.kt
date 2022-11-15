package com.whiterwalkers.parkezy.ui.parkinglots.manageparking

interface ParkingSlotCallback {

    fun onParkingRateClick(
        pos: Int
    )

    fun onParkingScheduleClick(
        pos: Int
    )

    fun onParkingDeleteClick(
        pos: Int
    )

    fun toggleAvailability(pos: Int, isAvailable: Boolean)

}