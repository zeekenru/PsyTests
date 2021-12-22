package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThreeScalesResult(
    override val testId: Int,
    override val testName: String,
    override val date: Long,
    val firstScale: Int,
    val secondScale: Int,
    val thirdScale: Int
) : HistoryItem, Parcelable