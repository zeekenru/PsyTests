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
import kotlinx.android.synthetic.main.activity_beck_depression.*
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject

class TestDActivity : AppCompatActivity(), View.OnClickListener {

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

    companion object {
        const val TEST_PARAM = "test"
        const val ANSWER_KEY_1 = 1
        const val ANSWER_KEY_3 = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oub)
        Logger.d(this.javaClass.simpleName)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            unfreezeInstanceState(savedInstanceState)
        } else {
            test = intent.getParcelableExtra(BeckDepressionActivity.TEST_PARAM)
            questionsCount = test.questions.size
            progress_bar.max = questionsCount
        }
        answer_btn_1.setOnClickListener(this)
        answer_btn_2.setOnClickListener(this)
        answer_btn_3.setOnClickListener(this)
        progress_bar.progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.primaryColor),
                android.graphics.PorterDuff.Mode.SRC_IN)
        back_navigation_btn.setOnClickListener(object : MenuItemDoubleClickListener() {
            override fun onSingleClick(item: View) {
                if (firstBackClick) {
                    showBackToast()
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
        } else {
            finish()
        }
    }

    private fun showQuestion() {
        if (currentQuestion <= 21) {
            Logger.d("show question : $currentQuestion")
            test_progress_count.text = "$currentQuestion/$questionsCount"
            progress_bar.progress = currentQuestion
            val question = test.questions[currentQuestion - 1]
            question_text.text = question.questionText
            answer_btn_1.text = question.answers!![0]
            answer_btn_2.text = question.answers!![1]
            answer_btn_3.text = question.answers!![2]
        } else {
            showResult()
            finish()
        }
    }

    private fun showResult() {
        val result = OneScaleResult(test.name!!, Date().time, test.interpretation, TestsTypes.OUB, summary)
        if (preferenceHelper.saveResultsEnabled()){
            dao.saveToHistory(result)
        }
        val intent = Intent(this, OneScaleResultActivity::class.java)
                .putExtra(OneScaleResultActivity.RESULT_DATA_PARAM, result)
        startActivity(intent)
    }

    private fun showBackToast() {
        Toast.makeText(this@TestDActivity, getString(R.string.double_click_to_close),
                Toast.LENGTH_SHORT).show()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.answer_btn_1 -> {
                if (test.questions[currentQuestion].key == ANSWER_KEY_1) {
                    summary += 2
                }
            }

            R.id.answer_btn_2 -> {
                summary += 1
            }
            R.id.answer_btn_3 -> {
                if (test.questions[currentQuestion].key == ANSWER_KEY_3) {
                    summary += 2
                }
            }
        }
        currentQuestion++
        showQuestion()
    }
}
