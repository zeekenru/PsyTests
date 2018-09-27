package com.kovapps.kovalev.psytests.ui.test

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.kovapps.kovalev.psytests.MenuItemDoubleClickListener
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.OneScaleResult
import com.kovapps.kovalev.psytests.enities.Test
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.result.OneScaleResultActivity
import com.orhanobut.logger.Logger
import com.tinsuke.icekick.extension.freezeInstanceState
import com.tinsuke.icekick.extension.parcelLateState
import com.tinsuke.icekick.extension.serialState
import com.tinsuke.icekick.extension.unfreezeInstanceState
import kotlinx.android.synthetic.main.activity_zung.*
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject

class TestEActivity : AppCompatActivity() {

    @Inject lateinit var dao : TestDao

    @Inject lateinit var preferenceHelper: PreferenceHelper

    private var firstBackClick: Boolean by serialState(true)

    private var questionsCount: Int by serialState(0)

    private var currentQuestion: Int by serialState(1)

    private var summary: Int by serialState(0)

    private var lastAddedValue: Int by serialState(0)

    private var test: Test by parcelLateState()


    companion object {
        const val TEST_PARAM = "test"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zung)
        Logger.d(this.javaClass.simpleName)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            unfreezeInstanceState(savedInstanceState)
        } else {
            test = intent.getParcelableExtra(TEST_PARAM)
        }
        questionsCount = test.questions.size
        progress_bar.max = questionsCount
        progress_bar.progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.primaryColor),
                android.graphics.PorterDuff.Mode.SRC_IN)
        answer_btn_1.text = test.answers!![0]
        answer_btn_2.text = test.answers!![1]
        answer_btn_3.text = test.answers!![2]
        answer_btn_4.text = test.answers!![3]
        val listener = {v : View ->
            when (v.id) {
                R.id.answer_btn_1 -> {
                    summary += if (test.questions[currentQuestion - 1].right!!) {
                        1
                    } else {
                        4
                    }
                }
                R.id.answer_btn_2 -> {
                    summary += if (test.questions[currentQuestion - 1].right!!) {
                        2
                    } else {
                        3
                    }
                }
                R.id.answer_btn_3 -> {
                    summary += if (test.questions[currentQuestion - 1].right!!) {
                        3
                    } else 2
                }

                R.id.answer_btn_4 -> {
                    summary += if (test.questions[currentQuestion - 1].right!!) {
                        4
                    } else 1
                }
            }
            currentQuestion++
            showQuestion()
        }
        val listener2 = {v : View ->
            when (v.id) {
                R.id.answer_btn_1 -> {
                    summary += if (test.questions[currentQuestion - 1].key == 1) {
                        2
                    } else 0

                }
                R.id.answer_btn_2 -> {
                    summary += if (test.questions[currentQuestion - 1].key == 1) {
                        1
                    } else 0

                }
                R.id.answer_btn_3 -> {
                    summary += if (test.questions[currentQuestion - 1].key == 0) {
                        0
                    } else 1
                }

                R.id.answer_btn_4 -> {
                    summary += if (test.questions[currentQuestion - 1].key == 0) {
                        2
                    } else 0
                }
            }
            currentQuestion++
            showQuestion()
        }
        val onClickListener = when (test.id){
            TestsTypes.ZUNG_DEPRESSION,TestsTypes.BECK_HOPELESSNESS, TestsTypes.ZUNG_ANXIETY -> listener
            TestsTypes.EQ -> listener2
            else -> throw IllegalArgumentException("Unexpected test id: ${test.id} ")
        }
        answer_btn_1.setOnClickListener(onClickListener)
        answer_btn_2.setOnClickListener(onClickListener)
        answer_btn_3.setOnClickListener(onClickListener)
        answer_btn_4.setOnClickListener(onClickListener)
        back_navigation_btn.setOnClickListener(object : MenuItemDoubleClickListener() {
            override fun onSingleClick(item: View) {
                if (firstBackClick) {
                    Toast.makeText(this@TestEActivity, getString(R.string.double_click_to_close),
                            Toast.LENGTH_SHORT).show()
                    firstBackClick = false
                }
                back()
            }

            override fun onDoubleClick(item: View) {
                finish()
            }
        })
        showQuestion()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        freezeInstanceState(outState)
    }

    private fun back() {
        if (currentQuestion != 1) {
            summary -= lastAddedValue
            currentQuestion--
            showQuestion()
        }
    }

    private fun showQuestion() {
        if (currentQuestion <= questionsCount) {
            Logger.d("show question : $currentQuestion")
            test_progress_count.text = "$currentQuestion/$questionsCount"
            progress_bar.progress = currentQuestion
            val question = test.questions[currentQuestion - 1]
            question_text.text = question.questionText
        } else {
            showResult()
            finish()
        }

    }

    private fun showResult() {
        val intent = Intent(this, OneScaleResultActivity::class.java)
        val type = when (test.id) {
            2 -> TestsTypes.ZUNG_DEPRESSION
            3 -> TestsTypes.BECK_HOPELESSNESS
            4 -> TestsTypes.ZUNG_ANXIETY
            12 -> TestsTypes.EQ
            else -> throw IllegalArgumentException("Wrong test id")
        }
        val result = OneScaleResult(test.name!!, Date().time, test.interpretation, type, summary)
        if (preferenceHelper.saveResultsEnabled()){
            dao.saveToHistory(result)
        }
        intent.putExtra(OneScaleResultActivity.RESULT_DATA_PARAM, result)
        startActivity(intent)
    }
}