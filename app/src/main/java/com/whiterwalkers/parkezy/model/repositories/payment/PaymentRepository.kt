package com.whiterwalkers.parkezy.model.repositories.payment

import com.whiterwalkers.parkezy.model.pojos.Payment

interface PaymentRepository {
    suspend fun getPaymentList(): List<Payment>
    suspend fun saveSelectedPayment(payment: Payment)
}