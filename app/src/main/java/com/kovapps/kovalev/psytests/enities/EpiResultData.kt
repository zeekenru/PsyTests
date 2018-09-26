package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpiResultData(val firstScale: Int,
                         val secondScale: Int,
                         val thirdScale: Int,
                         val interpretation: String) : Parcelable