package com.whiterwalkers.parkezy.model.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val make: String,
    val model: String,
    val nickName: String,
    val registrationNumber: Long,
    val type: Size
) : Parcelable
