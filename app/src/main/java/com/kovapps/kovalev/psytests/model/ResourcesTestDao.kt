package com.kovapps.kovalev.psytests.model

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.kovapps.kovalev.psytests.Categories
import com.kovapps.kovalev.psytests.enities.BaseTest
import com.kovapps.kovalev.psytests.enities.HistoryItem
import com.kovapps.kovalev.psytests.enities.PsyTest
import com.kovapps.kovalev.psytests.enities.SimpleTest
import io.paperdb.Paper
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject

class ResourcesTestDao @Inject constructor(val context: Context) : TestDao {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    companion object {
        private const val HISTORY = "history"
        private const val SIMPLE_TESTS = "simple_tests"
        private const val ALL_TESTS = "all_test"
        private const val COMPLETED_TESTS_IDS = "completed"
    }

    private val historyBook = Paper.book(HISTORY)

    private val completedBook = Paper.book(COMPLETED_TESTS_IDS)


    override fun getAllTests(): List<BaseTest> {
        val res = context.resources
        val jsonString =
            res.openRawResource(res.getIdentifier(ALL_TESTS, "raw", context.packageName))
                .bufferedReader().readText()
        val type = object : TypeToken<List<BaseTest>>() {}.type
        return parseArray(jsonString, type)
    }

    override fun getTestByCategory(category: Int): List<BaseTest> {
        val res = context.resources
        val jsonString =
            res.openRawResource(res.getIdentifier(ALL_TESTS, "raw", context.packageName))
                .bufferedReader().readText()
        val type = object : TypeToken<List<BaseTest>>() {}.type
        val tests = parseArray<List<BaseTest>>(jsonString, type)
        return when (category) {
            Categories.CAREER -> tests.filter { it.category == Categories.CAREER }
            Categories.RELATIONSHIPS -> tests.filter { it.category == Categories.RELATIONSHIPS }
            Categories.NEW -> tests.filter { it.category == Categories.NEW }
            else -> tests.filter { it.category == Categories.PSY }
        }
    }

    override fun getPsyTestById(id: Int): PsyTest {
        val res = context.resources
        val jsonString =
            res.openRawResource(res.getIdentifier("test_$id", "raw", context.packageName))
                .bufferedReader().readText()
        return gson.fromJson(jsonString, PsyTest::class.java)
    }

    override fun getTestById(id: Int): SimpleTest {
        val res = context.resources
        val jsonString =
            res.openRawResource(res.getIdentifier(SIMPLE_TESTS, "raw", context.packageName))
                .bufferedReader().readText()
        val type = object : TypeToken<List<SimpleTest>>() {}.type
        val tests = parseArray<List<SimpleTest>>(jsonString, type)
        return tests.filter { it.id == id }[0]
    }

    override fun getRandomTest(): BaseTest {
        val res = context.resources
        val s = res.openRawResource(res.getIdentifier(ALL_TESTS, "raw", context.packageName))
            .bufferedReader().readText()
        val type = object : TypeToken<List<BaseTest>>() {}.type
        val tests = parseArray<List<BaseTest>>(s, type)
        return tests[(1..tests.size).random() - 1]
    }

    override fun incrementCompletedTests(testId: Int) {
        if (!completedBook.contains(testId.toString())) {
            completedBook.write(testId.toString(), testId)
        }
    }

    override fun getCompletedTestsCount(): Int {
        return completedBook.allKeys.size
    }

    override fun saveToHistory(result: HistoryItem) {
        historyBook.write(result.testId.toString(), result)
    }

    override fun deleteAllHistory() {
        historyBook.destroy()
        completedBook.destroy()
    }

    override fun deleteFromHistory(id: Int) {
        historyBook.delete(id.toString())
    }

    override fun getHistory(): List<HistoryItem> {
        return if (historyBook.allKeys.isNullOrEmpty()) {
            emptyList()
        } else {
            historyBook.allKeys.map {
                historyBook.read(it)
            }
        }
    }

    private inline fun <reified T> parseArray(json: String, typeToken: Type): T {
        return gson.fromJson(json, typeToken)
    }

    private fun ClosedRange<Int>.random() =
        Random().nextInt((endInclusive + 1) - start) + start


}