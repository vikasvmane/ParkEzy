package com.whiterwalkers.parkezy.model.repositories.payment

import com.whiterwalkers.parkezy.model.datasource.payments.PaymentsDataSource
import com.whiterwalkers.parkezy.model.pojos.Payment
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(private val paymentsDataSource: PaymentsDataSource) :
    PaymentRepository {
    override suspend fun getPaymentList(): List<Payment> {
        val mutableList = mutableListOf<Payment>()
        paymentsDataSource.getPaymentOptions().collect { list ->
            mutableList.addAll(list)
        }
        return mutableList
    }

    override suspend fun saveSelectedPayment(payment: Payment) {
        paymentsDataSource.saveSelectedPayment(payment)
    }
}