package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ThreeScalesResult(
        override val id: Int,
        override val name: String,
        override val time: Long,
        override val interpretation: String,
        val firstScale: Int,
        val secondScale: Int,
        val thirdScale: Int) : Result, Parcelable