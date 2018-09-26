package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultData(val testName : String,
                      val summary : Int,
                      val testType : Int,
                      val interpretation : String,
                      val time : Long) : Parcelable