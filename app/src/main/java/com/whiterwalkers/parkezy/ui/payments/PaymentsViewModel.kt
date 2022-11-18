package com.whiterwalkers.parkezy.ui.payments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whiterwalkers.parkezy.model.pojos.ParkingSpot
import com.whiterwalkers.parkezy.model.repositories.manageparking.ManageParkingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(private val manageParkingRepository: ManageParkingRepository) : ViewModel() {

    private var _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading

    private var _success = MutableLiveData<Boolean>()
    var success: LiveData<Boolean> = _success

    suspend fun createParking(parkingSpot: ParkingSpot) {
        _loading.value = true
        val response = manageParkingRepository.createParkingSpot(parkingSpot)
        _loading.value = false
        _success.value = response.isSuccess

    }
}