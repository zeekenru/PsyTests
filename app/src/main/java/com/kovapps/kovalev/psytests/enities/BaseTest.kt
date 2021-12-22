package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BaseTest(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("duration")
    val duration: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("category")
    val category: Int,
    @SerializedName("questions_count")
    val questionsCount: Int?
) : Parcelable
