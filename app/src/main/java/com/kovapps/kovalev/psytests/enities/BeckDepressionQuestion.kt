package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BeckDepressionQuestion(val number: Int,
                                  @SerializedName("question") val questionText: String,
                                  val answers: List<String>,
                                  val key : Int) : Parcelable