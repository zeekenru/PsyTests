package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Test(val id: Int,
                val name: String?,
                @SerializedName("questions_count")
                val questionsCount: Int?,
                val interpretation : String,
                val description : String?,
                val duration : String?,
                @SerializedName("scales_count")
                val scalesCount : Int,
                val questions : List<TestQuestion>,
                val answers : List<String>?) : Parcelable
