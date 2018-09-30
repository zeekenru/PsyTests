package com.kovapps.kovalev.psytests.model

import android.content.Context
import com.kovapps.kovalev.psytests.enities.*
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.paperdb.Paper
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject


class AssetsTestDao @Inject constructor(val context: Context): TestDao {

    private val gson = GsonBuilder().create()

    private val assetManager = context.assets

    companion object {
       private const val HISTORY_ITEMS = "history_items"
    }

    override fun saveToHistory(result : Result){
        val items : MutableList<Result> = if (Paper.book().contains(HISTORY_ITEMS)){
            Paper.book().read(HISTORY_ITEMS)
        } else mutableListOf()
        items.add(result)
        Paper.book().write(HISTORY_ITEMS, items)
    }

    override fun deleteAllHistory() {
        Paper.book().delete(HISTORY_ITEMS)
        Paper.book().destroy()
    }

    override fun deleteFromHistory(position: Int) {
        val items : MutableList<Result> = Paper.book().read(HISTORY_ITEMS)
        items.removeAt(position)
        Paper.book().write(HISTORY_ITEMS, items)
    }

    override fun getHistory() : List<Result>{
        return if (!Paper.book().contains(HISTORY_ITEMS)) return emptyList()
        else Paper.book().read(HISTORY_ITEMS)
    }

    override fun getTestById(id: Int): Test {
        val jsonString = assetManager.open("test_$id.json").bufferedReader().readText()
        return gson.fromJson<Test>(jsonString, Test::class.java)
    }

    override fun getRandomTest(): Test {
        val s = assetManager.open("tests").bufferedReader().readText()
        val type = object : TypeToken<List<Test>>() {}.type
        val tests = parseArray<List<Test>>(s, type)
        return tests[(0..tests.size).random()]
    }

    override fun getAllTests(): List<Test> {
        val jsonString = assetManager.open("tests").bufferedReader().readText()
        val type = object : TypeToken<List<Test>>(){}.type
        return parseArray(jsonString, type)
    }

    override fun getTestDescriptionById(id: Int): String {
      return  assetManager.open("md/test_$id.md").bufferedReader().readText()
    }

    private inline fun <reified T> parseArray(json: String, typeToken: Type): T {
        return gson.fromJson<T>(json, typeToken)
    }

    private fun ClosedRange<Int>.random() =
            Random().nextInt((endInclusive + 1) - start) + start
}