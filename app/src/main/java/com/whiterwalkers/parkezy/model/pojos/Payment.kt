package com.whiterwalkers.parkezy.model.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Payment(
    val paymentId: Int,
    val paymentName: String,
    val icon: Int,
    val balance: String? = null,
    var isPrimary: Boolean
) : Parcelable