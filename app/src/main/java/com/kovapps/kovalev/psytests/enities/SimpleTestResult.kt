package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleTestResult(
    override val testId: Int,
    override val testName: String,
    override val date: Long,
    val points: Int,
    val results: List<Result>
) : HistoryItem, Parcelable
