package com.kovapps.kovalev.psytests.model

import com.kovapps.kovalev.psytests.enities.*


interface TestDao {
    fun getHistory() : List<Result>
    fun saveToHistory(result : Result)
    fun getRandomTest() : Test
    fun getYesNoTest(id : Int) : YesNoTest
    fun getBeckTest(id : Int) : BeckDepressionTest
    fun deleteFromHistory(position : Int)
    fun getAllTests() : List<Test>
    fun getZungTest(id : Int): ZungTest
    fun getTestById(id: Int): Test
    fun deleteAllHistory()
}