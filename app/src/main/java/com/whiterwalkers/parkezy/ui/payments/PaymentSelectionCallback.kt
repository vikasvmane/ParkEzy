package com.whiterwalkers.parkezy.ui.payments

import com.whiterwalkers.parkezy.model.pojos.Payment

interface PaymentSelectionCallback {
    fun onSelectedPaymentOption(payment: Payment)
}