package com.whiterwalkers.parkezy.model.datasource.payments

import com.whiterwalkers.parkezy.model.pojos.Payment
import kotlinx.coroutines.flow.Flow

interface PaymentsDataSource {
    fun getPaymentOptions(): Flow<List<Payment>>
    suspend fun saveSelectedPayment(payment: Payment)
}