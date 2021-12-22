package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PsyTestQuestion(
    val number: Int,
    @SerializedName("question")
    val questionText: String,
    @SerializedName("answers", alternate = [])
    val answers: List<String>?,
    val key: Int?,
    val scale: Int?,
    val right: Boolean?
) : Parcelable