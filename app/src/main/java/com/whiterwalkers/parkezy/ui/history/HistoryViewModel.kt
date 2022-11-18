package com.whiterwalkers.parkezy.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whiterwalkers.parkezy.model.pojos.Transaction
import com.whiterwalkers.parkezy.model.repositories.history.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val historyRepository: HistoryRepository) :
    ViewModel() {

    private var _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading

    private var _success = MutableLiveData<Boolean>()
    var success: LiveData<Boolean> = _success

    private var _transactions = MutableLiveData<List<Transaction>>()
    var transaction: LiveData<List<Transaction>> = _transactions

    suspend fun getParkingHistory(userId: Int) {
        _loading.value = true
        val response = historyRepository.getParkingHistory(userId)
        _loading.value = false
        _success.value = true
        _transactions.value = response
    }
}