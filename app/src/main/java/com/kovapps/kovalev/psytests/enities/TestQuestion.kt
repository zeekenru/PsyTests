package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TestQuestion(val number : Int,
                        @SerializedName("question")
                        val questionText: String,
                        val answers: List<String>?,
                        val key : Int?,
                        val scale : Int?,
                        val right : Boolean?) : Parcelable