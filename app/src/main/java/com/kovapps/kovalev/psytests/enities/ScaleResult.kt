package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScaleResult(
        override val id: Int,
        override val name: String,
        override val time: Long,
        override val interpretation: String,
        val tesType: Int,
        val scalesValues: List<Int>) : Result, Parcelable