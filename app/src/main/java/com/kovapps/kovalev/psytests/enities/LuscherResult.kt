package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
data class LuscherResult(
        override val id: Int,
        override val name: String = "Тест люшера",
                         override val time: Long,
                         override var interpretation: String,
                         val answers : ArrayList<LusherColor>)
    : Result, Parcelable