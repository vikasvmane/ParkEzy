package com.whiterwalkers.parkezy.ui.payments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whiterwalkers.parkezy.model.pojos.Payment
import com.whiterwalkers.parkezy.model.repositories.payment.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(private val paymentRepository: PaymentRepository) :
    ViewModel() {

    private var _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading

    private var _success = MutableLiveData<Boolean>()
    var success: LiveData<Boolean> = _success
    private var _transactions = MutableLiveData<List<Payment>>()
    var transaction: LiveData<List<Payment>> = _transactions

    suspend fun getPaymentList() {
        _loading.value = true
        val response = paymentRepository.getPaymentList()
        _loading.value = false
        _success.value = true
        _transactions.value = response
    }

    suspend fun saveSelectedPayment(payment: Payment) {
        paymentRepository.saveSelectedPayment(payment)
    }
}