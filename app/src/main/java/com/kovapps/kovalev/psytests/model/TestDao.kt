package com.kovapps.kovalev.psytests.model

import com.kovapps.kovalev.psytests.enities.*


interface TestDao {
    fun getHistory() : List<Result>
    fun saveToHistory(result : Result)
    fun getRandomTest() : Test
    fun deleteFromHistory(position : Int)
    fun getAllTests() : List<Test>
    fun getTestById(id: Int): Test
    fun deleteAllHistory()
    fun getTestDescriptionById(id : Int) : String
}