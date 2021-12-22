package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OneScaleResult(
    override val testId: Int,
    override val testName: String,
    override val date: Long,
    val scale: Int
) : HistoryItem, Parcelable
