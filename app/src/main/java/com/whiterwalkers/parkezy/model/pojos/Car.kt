package com.whiterwalkers.parkezy.model.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val vehicleId: Int? = null,
    val make: String,
    val model: String,
    val nickName: String,
    val registrationNumber: Long,
    val type: Size,
    var isPrimary: Boolean
) : Parcelable
