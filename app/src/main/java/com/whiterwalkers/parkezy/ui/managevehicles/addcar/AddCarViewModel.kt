package com.whiterwalkers.parkezy.ui.managevehicles.addcar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whiterwalkers.parkezy.model.pojos.Car
import com.whiterwalkers.parkezy.model.repositories.manageVehicles.IManageVehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCarViewModel @Inject constructor(private val mapRepository: IManageVehicleRepository) :
    ViewModel() {

    private var _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading

    private var _success = MutableLiveData<Boolean>()
    var success: LiveData<Boolean> = _success

    suspend fun addCar(car: Car) {
        _loading.value = true
        val response = mapRepository.addCar(car)
        _loading.value = false
        _success.value = response.isSuccess

    }
}