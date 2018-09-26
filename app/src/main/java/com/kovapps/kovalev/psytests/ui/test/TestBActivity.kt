package com.kovapps.kovalev.psytests.ui.test

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import com.kovapps.kovalev.psytests.MenuItemDoubleClickListener
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.Test
import com.kovapps.kovalev.psytests.enities.ThreeScalesResult
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.result.ThreeScalesResultActivity
import com.orhanobut.logger.Logger
import com.tinsuke.icekick.extension.freezeInstanceState
import com.tinsuke.icekick.extension.parcelLateState
import com.tinsuke.icekick.extension.serialState
import com.tinsuke.icekick.extension.unfreezeInstanceState
import kotlinx.android.synthetic.main.activity_yes_no_test.*
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject

class TestBActivity : AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var dao : TestDao

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private var firstBackClick: Boolean by serialState(true)

    private var questionsCount: Int by serialState(0)

    private var currentQuestion: Int by serialState(1)

    private var summary: Int by serialState(0)

    private var firstScale: Int by serialState(0)

    private var secondScale: Int by serialState(0)

    private var thirdScale: Int by serialState(0)

    private var lastAddedValue: Int by serialState(0)

    private var test: Test by parcelLateState()

    companion object {
        const val TEST_PARAM = "test"
        const val FIRST_SCALE = 1
        const val SECOND_SCALE = 2
        const val THIRD_SCALE = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yes_no_test)
        Logger.d(this.javaClass.simpleName)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            unfreezeInstanceState(savedInstanceState)
        } else {
            test = intent.getParcelableExtra(TEST_PARAM)
            questionsCount = test.questions.size
            progress_bar.max = questionsCount
        }
        progress_bar.progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.primaryColor),
                android.graphics.PorterDuff.Mode.SRC_IN)
        answer_btn_1.setOnClickListener(this)
        answer_btn_2.setOnClickListener(this)
        back_navigation_btn.setOnClickListener(object : MenuItemDoubleClickListener() {
            override fun onSingleClick(item: View) {
                if (firstBackClick) {
                    Toast.makeText(this@TestBActivity, getString(R.string.double_click_to_close),
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
            when (test.questions[currentQuestion - 1].scale) {
                FIRST_SCALE -> firstScale -= lastAddedValue
                SECOND_SCALE -> secondScale -= lastAddedValue
                THIRD_SCALE -> thirdScale -= lastAddedValue
            }
            summary -= lastAddedValue
            currentQuestion--
            showQuestion()
        } else {
            showBackToast()
        }
    }

    private fun showQuestion() {
        if (currentQuestion < questionsCount) {
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
        val result = ThreeScalesResult(test.name!!, Date().time, test.interpretation, TestsTypes.EPI,
                firstScale, secondScale, thirdScale)
        if (preferenceHelper.saveResultsEnabled()){
            dao.saveToHistory(result)
        }
        val intent = Intent(this, ThreeScalesResultActivity::class.java)
                .putExtra(ThreeScalesResultActivity.RESULT_DATA_PARAM,
                        result)
        startActivity(intent)
        finish()
    }

    private fun showBackToast() {
        Toast.makeText(this@TestBActivity, getString(R.string.double_click_to_close),
                Toast.LENGTH_SHORT).show()
    }

    override fun onClick(view: View) {
        Logger.d("onCLick")
        val question = test.questions[currentQuestion]
        when (view.id) {
            R.id.answer_btn_1 -> {
                val sum = if (question.right!!) 1 else 0
                when (question.scale) {
                    FIRST_SCALE -> firstScale += sum
                    SECOND_SCALE -> secondScale += sum
                    THIRD_SCALE -> thirdScale += sum
                }
                lastAddedValue = sum
            }

            R.id.answer_btn_2 -> {
                val sum = if (!question.right!!) 1 else 0
                when (question.scale) {
                    FIRST_SCALE -> firstScale += sum
                    SECOND_SCALE -> secondScale += sum
                    THIRD_SCALE -> thirdScale += sum
                }
                lastAddedValue = sum
            }

        }
        currentQuestion++
        showQuestion()
    }
}
