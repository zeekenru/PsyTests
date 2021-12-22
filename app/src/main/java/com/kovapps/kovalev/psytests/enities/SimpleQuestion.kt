package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleQuestion(
    @SerializedName("number")
    val number: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("answers")
    val answers: List<SimpleTestAnswer>
) : Parcelable
