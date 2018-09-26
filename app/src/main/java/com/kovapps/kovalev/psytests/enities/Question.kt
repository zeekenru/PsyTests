package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
        @SerializedName("text")
        val text : String,
        @SerializedName("answer")
                    val answer : String) : Parcelable