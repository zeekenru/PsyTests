package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleTestAnswer(
    @SerializedName("text")
    val text: String,
    @SerializedName("points")
    val points: Int
) : Parcelable
