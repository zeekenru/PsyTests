package com.kovapps.kovalev.psytests.ui.test

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ScrollView
import android.widget.Toast
import com.kovapps.kovalev.psytests.MenuItemDoubleClickListener
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.OneScaleResult
import com.kovapps.kovalev.psytests.enities.Result
import com.kovapps.kovalev.psytests.enities.Test
import com.kovapps.kovalev.psytests.enities.ThreeScalesResult
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.result.OneScaleResultActivity
import com.kovapps.kovalev.psytests.ui.result.ThreeScalesResultActivity
import com.orhanobut.logger.Logger
import com.tinsuke.icekick.extension.freezeInstanceState
import com.tinsuke.icekick.extension.parcelLateState
import com.tinsuke.icekick.extension.serialState
import com.tinsuke.icekick.extension.unfreezeInstanceState
import kotlinx.android.synthetic.main.activity_karpov.*
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject

class TestCActivity : AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var dao: TestDao

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private var firstBackClick: Boolean by serialState(true)

    private var questionsCount: Int by serialState(0)

    private var currentQuestion: Int by serialState(1)

    private var summary: Int by serialState(0)

    private var lastAddedValue: Int by serialState(0)

    private var test: Test by parcelLateState()

    private var maslachFirstScale: Int by serialState(0)

    private var maslachSecondScale: Int by serialState(0)

    private var maslachThirdScale: Int by serialState(0)

    companion object {
        const val TEST_PARAM = "test"
        const val KARPOV_ID = 5
        const val MASLACH_ID = 6
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_karpov)
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
        initAnswerButtons()
        back_navigation_btn.setOnClickListener(object : MenuItemDoubleClickListener() {
            override fun onSingleClick(item: View) {
                if (firstBackClick) {
                    Toast.makeText(this@TestCActivity, getString(R.string.double_click_to_close),
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


    override fun onClick(v: View) {
        when (test.id) {
            KARPOV_ID -> {
                when (v.id) {
                    R.id.answer_btn_1 -> {
                        summary += if (test.questions[currentQuestion - 1].right!!) {
                            1
                        } else {
                            7
                        }
                    }
                    R.id.answer_btn_2 -> {
                        summary += if (test.questions[currentQuestion - 1].right!!) {
                            2
                        } else {
                            6
                        }
                    }
                    R.id.answer_btn_3 -> {
                        summary += if (test.questions[currentQuestion - 1].right!!) {
                            3
                        } else 5
                    }
                    R.id.answer_btn_4 -> {
                        summary += 4
                    }
                    R.id.answer_btn_5 -> {
                        summary += if (test.questions[currentQuestion - 1].right!!) {
                            5
                        } else 3
                    }
                    R.id.answer_btn_6 -> {
                        summary += if (test.questions[currentQuestion - 1].right!!) {
                            6
                        } else 2
                    }
                    R.id.answer_btn_7 -> {
                        summary += if (test.questions[currentQuestion - 1].right!!) {
                            7
                        } else 1
                    }


                }
            }
            MASLACH_ID -> {
                var sum = 0
                when (v.id) {
                    R.id.answer_btn_1 -> {
                        sum += if (test.questions[currentQuestion - 1].right!!) {
                            0
                        } else {
                            6
                        }
                    }
                    R.id.answer_btn_2 -> {
                        sum += if (test.questions[currentQuestion - 1].right!!) {
                            1
                        } else {
                            5
                        }
                    }
                    R.id.answer_btn_3 -> {
                        sum += if (test.questions[currentQuestion - 1].right!!) {
                            2
                        } else 4
                    }
                    R.id.answer_btn_4 -> {
                        sum += 3
                    }
                    R.id.answer_btn_5 -> {
                        sum += if (test.questions[currentQuestion - 1].right!!) {
                            4
                        } else 2
                    }
                    R.id.answer_btn_6 -> {
                        sum += if (test.questions[currentQuestion - 1].right!!) {
                            5
                        } else 1
                    }
                    R.id.answer_btn_7 -> {
                        sum += if (test.questions[currentQuestion - 1].right!!) {
                            6
                        } else 0
                    }
                }
                when (currentQuestion) {
                    1, 2, 3, 8, 13, 14, 16, 20 -> maslachFirstScale += sum
                    5, 10, 11, 15, 22 -> maslachSecondScale += sum
                    else -> maslachThirdScale += sum
                }
            }
        }

        currentQuestion++
        showQuestion()
    }

    private fun initAnswerButtons() {
        answer_btn_1.text = test.answers!![0]
        answer_btn_2.text = test.answers!![1]
        answer_btn_3.text = test.answers!![2]
        answer_btn_4.text = test.answers!![3]
        answer_btn_5.text = test.answers!![4]
        answer_btn_6.text = test.answers!![5]
        answer_btn_7.text = test.answers!![6]
        answer_btn_1.setOnClickListener(this)
        answer_btn_2.setOnClickListener(this)
        answer_btn_3.setOnClickListener(this)
        answer_btn_4.setOnClickListener(this)
        answer_btn_5.setOnClickListener(this)
        answer_btn_6.setOnClickListener(this)
        answer_btn_7.setOnClickListener(this)
    }

    private fun back() {
        if (currentQuestion != 1) {
            when (currentQuestion) {
                1, 2, 3, 8, 13, 14, 16, 20 -> maslachFirstScale -= lastAddedValue
                5, 10, 11, 15, 22 -> maslachSecondScale -= lastAddedValue
                else -> maslachThirdScale -= lastAddedValue
            }
            summary -= lastAddedValue
            currentQuestion--
            showQuestion()
        }
    }

    private fun showQuestion() {
        if (currentQuestion > questionsCount) {
            showResult()
            finish()
        } else {
            Logger.d("show question : $currentQuestion")
            root_scroll_view.fullScroll(ScrollView.FOCUS_UP)
            test_progress_count.text = "$currentQuestion/$questionsCount"
            progress_bar.progress = currentQuestion
            val question = test.questions[currentQuestion - 1]
            question_text.text = question.questionText
        }

    }


    private fun showResult() {
        val testType = when (test.id) {
            KARPOV_ID -> TestsTypes.KARPOV_REFLECTION
            MASLACH_ID -> TestsTypes.MASLACH
            else -> throw IllegalArgumentException("Unexpected argument")
        }
        lateinit var intent: Intent
        lateinit var result: Result
        when (test.id) {
            KARPOV_ID -> {
                result = OneScaleResult(test.name!!, Date().time, test.interpretation, testType, summary)
                intent = Intent(this, OneScaleResultActivity::class.java)
                        .putExtra(OneScaleResultActivity.RESULT_DATA_PARAM, result)
            }
            MASLACH_ID -> {
                result = ThreeScalesResult(test.name!!, Date().time,
                        test.interpretation, testType, maslachFirstScale, maslachSecondScale, maslachThirdScale)
                intent = Intent(this, ThreeScalesResultActivity::class.java)
                        .putExtra(ThreeScalesResultActivity.RESULT_DATA_PARAM, result)
            }
            else -> throw IllegalArgumentException("Unexpected argument")
        }
        if (preferenceHelper.saveResultsEnabled()){
            dao.saveToHistory(result)
        }
        startActivity(intent)
        finish()
    }

}
