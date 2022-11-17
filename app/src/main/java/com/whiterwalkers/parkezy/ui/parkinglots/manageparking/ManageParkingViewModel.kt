package com.whiterwalkers.parkezy.ui.parkinglots.manageparking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import com.whiterwalkers.parkezy.model.repositories.manageparking.ManageParkingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageParkingViewModel @Inject constructor(private val manageParkingRepository: ManageParkingRepository) : ViewModel() {

    private var _parkingSpotLiveData = MutableLiveData<List<ParkingSpot>>()
    var parkingSpotLiveData: LiveData<List<ParkingSpot>> = _parkingSpotLiveData

    suspend fun getParkingSpot(userId: Int) {
        _parkingSpotLiveData.value = manageParkingRepository.getUserParkingSpots(userId)
    }
}