package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BeckDepressionTest(val id : Int,
                              val questions : List<BeckDepressionQuestion>,
                              val interpretation : String) : Parcelable