package com.kovapps.kovalev.psytests.enities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class LuscherResult(
    override val testId: Int,
    override val testName: String,
    override val date: Long,
    val answers: ArrayList<LusherColor>
) : HistoryItem, Parcelable