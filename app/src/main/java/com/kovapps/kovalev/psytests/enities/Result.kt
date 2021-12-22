package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val min: Int,
    val max: Int,
    val text: String
) : Parcelable
