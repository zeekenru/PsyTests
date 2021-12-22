package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleTest(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("category")
    val category: Int,
    @SerializedName("questions")
    val questions: List<SimpleQuestion>,
    @SerializedName("results")
    val results: List<Result>
) : Parcelable
