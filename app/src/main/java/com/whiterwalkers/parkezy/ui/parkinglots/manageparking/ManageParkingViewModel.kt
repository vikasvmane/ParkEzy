package com.whiterwalkers.parkezy.ui.parkinglots.manageparking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whiterwalkers.parkezy.model.MapRepository
import com.whiterwalkers.parkezy.model.pojos.Location
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageParkingViewModel @Inject constructor(private val mapRepository: MapRepository) : ViewModel() {

    private var _parkingSpotLiveData = MutableLiveData<List<ParkingSpot>>()
    var parkingSpotLiveData: LiveData<List<ParkingSpot>> = _parkingSpotLiveData

    suspend fun getParkingSpot(userId: Int) {
        _parkingSpotLiveData.value = mapRepository.getUserParkingSpots(userId)
    }
}