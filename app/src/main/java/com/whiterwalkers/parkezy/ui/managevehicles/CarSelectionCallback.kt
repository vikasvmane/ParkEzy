package com.whiterwalkers.parkezy.ui.managevehicles

import com.whiterwalkers.parkezy.model.pojos.Car

interface CarSelectionCallback {
    fun onSelectedVehicle(car: Car)
}