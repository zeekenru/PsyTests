package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class YesNoTest(
        val questions: List<YesNoQuestion>,
        @SerializedName("questions_count")
        val questionsCount: Int,
        val interpretation : String) : Parcelable