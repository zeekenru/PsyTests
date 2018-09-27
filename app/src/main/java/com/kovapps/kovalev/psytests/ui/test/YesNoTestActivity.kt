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
import com.kovapps.kovalev.psytests.enities.*
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.result.OneScaleResultActivity
import com.kovapps.kovalev.psytests.ui.result.OstResultActivity
import com.orhanobut.logger.Logger
import com.tinsuke.icekick.extension.freezeInstanceState
import com.tinsuke.icekick.extension.parcelLateState
import com.tinsuke.icekick.extension.serialState
import com.tinsuke.icekick.extension.unfreezeInstanceState
import kotlinx.android.synthetic.main.activity_yes_no_test.*
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class YesNoTestActivity : AppCompatActivity() {

    companion object {
        const val TEST_PARAM = "test"
    }

    @Inject
    lateinit var dao: TestDao

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val scalesValues: Array<Int> by serialState(arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0))

    private var firstBackClick: Boolean by serialState(true)

    private var questionsCount: Int by serialState(0)

    private var currentQuestion: Int by serialState(1)

    private var summary: Int by serialState(0)

    private var lastAddedValue: Int by serialState(0)

    private var test: Test by parcelLateState()

    private var lastEditedScale = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yes_no_test)
        Logger.d(this.javaClass.simpleName)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            unfreezeInstanceState(savedInstanceState)
        } else {
            test = intent.getParcelableExtra(TestEActivity.TEST_PARAM)
        }
        questionsCount = test.questions.size
        progress_bar.max = questionsCount
        answer_btn_1.setOnClickListener {
            val question = test.questions[currentQuestion - 1]
            if (question.key == 1) {
                scalesValues[question.scale!! - 1] += 1
                lastEditedScale = question.scale!! - 1
                lastAddedValue += 1
            }
            summary++
            currentQuestion++
            showQuestion()
        }
        answer_btn_2.setOnClickListener {
            val question = test.questions[currentQuestion]
            if (question.key == 0) {
                scalesValues[question.scale!! - 1] += 1
                lastEditedScale = question.scale!! - 1
                lastAddedValue += 1
            }
            currentQuestion++
            showQuestion()
        }
        progress_bar.progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.primaryColor),
                android.graphics.PorterDuff.Mode.SRC_IN)
        back_navigation_btn.setOnClickListener(object : MenuItemDoubleClickListener() {
            override fun onSingleClick(item: View) {
                if (firstBackClick) {
                    Toast.makeText(this@YesNoTestActivity, getString(R.string.double_click_to_close),
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
            scalesValues[lastEditedScale] -= lastAddedValue
            summary -= lastAddedValue
            currentQuestion--
            showQuestion()
        }
    }

    private fun showQuestion() {
        if (currentQuestion < test.questionsCount!!) {
            Logger.d("Number : $currentQuestion")
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
        lateinit var result: Result
        lateinit var intent : Intent
        Logger.d("values: ${Arrays.deepToString(scalesValues)}")
        if (test.id == 11) {
            result = ScaleResult(test.name!!, Date().time, test.interpretation, TestsTypes.OST,
                    scalesValues.toCollection(ArrayList()))
            intent = Intent(this, OstResultActivity::class.java)
            intent.putExtra(OstResultActivity.RESULT_DATA_PARAM, result)

        } else {
            result = OneScaleResult(test.name!!, Date().time, test.interpretation, TestsTypes.OUB, summary)
            intent = Intent(this, OneScaleResultActivity::class.java)
            intent.putExtra(OneScaleResultActivity.RESULT_DATA_PARAM, result)
        }
        if (preferenceHelper.saveResultsEnabled()) {
            dao.saveToHistory(result)
        }
        startActivity(intent)
        finish()
    }


}
