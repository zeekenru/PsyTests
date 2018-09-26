package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ZungTest(val id : Int,
                    val answers : List<String>,
                    val questions : List<ZungQuestion>,
                    val interpretation : String) : Parcelable