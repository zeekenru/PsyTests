package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScaleResult(
    override val testId: Int,
    override val testName: String,
    override val date: Long,
    val tesType: Int,
    val scalesValues: List<Int>
) : HistoryItem, Parcelable