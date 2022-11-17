package com.whiterwalkers.parkezy.ui.managevehicles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whiterwalkers.parkezy.model.repositories.manageVehicles.IManageVehicleRepository
import com.whiterwalkers.parkezy.model.pojos.Car
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageVehicleViewModel @Inject constructor(private val mapRepository: IManageVehicleRepository) :
    ViewModel() {

    private var _carLiveData = MutableLiveData<List<Car>>()
    var carListData: LiveData<List<Car>> = _carLiveData

    suspend fun getVehicleList(userId: Int) {
        _carLiveData.value = mapRepository.getCarList(userId)
    }
}