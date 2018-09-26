package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class YesNoQuestion(val number : Int,
                         @SerializedName("question")
                         val text : String,
                         val key : Int) : Parcelable