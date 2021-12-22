package com.kovapps.kovalev.psytests.model

import com.kovapps.kovalev.psytests.enities.BaseTest
import com.kovapps.kovalev.psytests.enities.HistoryItem
import com.kovapps.kovalev.psytests.enities.PsyTest
import com.kovapps.kovalev.psytests.enities.SimpleTest


interface TestDao {
    fun getHistory(): List<HistoryItem>
    fun saveToHistory(result: HistoryItem)
    fun getRandomTest(): BaseTest
    fun incrementCompletedTests(testId: Int)
    fun getCompletedTestsCount(): Int
    fun deleteFromHistory(id: Int)
    fun getPsyTestById(id: Int): PsyTest
    fun getTestById(id: Int): SimpleTest
    fun getAllTests(): List<BaseTest>
    fun deleteAllHistory()
    fun getTestByCategory(category: Int): List<BaseTest>
}