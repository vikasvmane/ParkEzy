package com.whiterwalkers.parkezy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whiterwalkers.parkezy.model.MapRepository
import com.whiterwalkers.parkezy.model.pojos.Location
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val mapRepository: MapRepository) : ViewModel() {
    private var mutableParkingSpotLiveData = MutableLiveData<List<ParkingSpot>>()
    var parkingSpotLiveData: LiveData<List<ParkingSpot>> = mutableParkingSpotLiveData

    suspend fun getNearByParkingSpot(currentLocation: Location) {
        mutableParkingSpotLiveData.value = mapRepository.getNearByParkingSpots(currentLocation)
    }
}