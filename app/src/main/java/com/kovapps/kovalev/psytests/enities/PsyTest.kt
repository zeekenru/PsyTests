package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PsyTest(
    val id: Int,
    val name: String,
    @SerializedName("questions_count")
    val questionsCount: Int?,
    val interpretation: String,
    val description: String?,
    val duration: String?,
    @SerializedName("scales_count")
    val scalesCount: Int,
    @SerializedName("questions", alternate = [])
    val questions: List<PsyTestQuestion>?,
    @SerializedName("answers", alternate = [])
    val answers: List<String>?
) : Parcelable
