package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OneScaleResult(
        override val name: String,
        override val time: Long,
        override val interpretation: String,
        val testType : Int,
        val scale : Int)
    : Result, Parcelable
