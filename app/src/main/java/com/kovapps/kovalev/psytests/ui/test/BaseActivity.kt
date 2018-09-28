package com.kovapps.kovalev.psytests.ui.test

import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.kovapps.kovalev.psytests.MenuItemDoubleClickListener
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.*
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.result.BassResultActivity
import com.kovapps.kovalev.psytests.ui.result.OneScaleResultActivity
import com.kovapps.kovalev.psytests.ui.result.OstResultActivity
import com.kovapps.kovalev.psytests.ui.result.ThreeScalesResultActivity
import com.orhanobut.logger.Logger
import com.tinsuke.icekick.extension.freezeInstanceState
import com.tinsuke.icekick.extension.parcelLateState
import com.tinsuke.icekick.extension.serialState
import com.tinsuke.icekick.extension.unfreezeInstanceState
import kotlinx.android.synthetic.main.base_activity.*
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class BaseActivity : AppCompatActivity() {

    companion object {
        const val TEST_PARAM = "test"
        private const val VALUES_RESTORE_KEY = "values"
    }

    private var firstBackClick: Boolean by serialState(true)

    private var questionsCount: Int by serialState(0)

    private var currentQuestion: Int by serialState(1)

    private val scalesValues = mutableListOf<Int>()

    private var test: Test by parcelLateState()

    private var scalesCount by serialState(0)

    private var lastEditedScale by serialState(-1)

    private var answersCount by serialState(0)

    private val buttons = mutableListOf<Button>()

    private lateinit var interstitialAd: InterstitialAd

    private val addedValuesStack = Stack<Int>()

    private var isRestoredState = false

    @Inject
    lateinit var dao: TestDao

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            isRestoredState = true
            unfreezeInstanceState(savedInstanceState)
            scalesValues.addAll(savedInstanceState.getIntegerArrayList(VALUES_RESTORE_KEY)!!)
            Logger.d("OnRestoreInstanceState")
        } else {
            test = intent.getParcelableExtra(TEST_PARAM)
            scalesCount = test.scalesCount
        }
        prepareAdView()
        questionsCount = test.questions.size
        for (i in 0 until scalesCount) {
            if (!isRestoredState) scalesValues.add(i, 0)
        }
        progress_bar.max = questionsCount
        progress_bar.progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.primaryColor),
                android.graphics.PorterDuff.Mode.SRC_IN)
        back_navigation_btn.setOnClickListener(object : MenuItemDoubleClickListener() {
            override fun onSingleClick(item: View) {
                if (firstBackClick || currentQuestion == 1) {
                    showBackToast()
                    firstBackClick = false
                } else {
                    back()
                }

            }

            override fun onDoubleClick(item: View) {
                finish()
            }
        })
        if (test.answers != null) {
            answersCount = test.answers!!.size
            for (i in 0 until answersCount) {
                addButton(test.answers!![i])
            }
        } else {
            answersCount = test.questions[currentQuestion].answers!!.size
            for (i in 0 until answersCount) {
                addButton(test.questions[currentQuestion - 1].answers!![i])
            }
        }
        initListeners()
        showQuestion()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Logger.d("OnSaveInstanceState")
        outState.putIntegerArrayList(VALUES_RESTORE_KEY, ArrayList(scalesValues))
        freezeInstanceState(outState)
    }

    private fun showBackToast() {
        Toast.makeText(this@BaseActivity, getString(R.string.double_click_to_close),
                Toast.LENGTH_SHORT).show()
    }

    private fun prepareAdView() {
        ad_view.loadAd(AdRequest.Builder().build())
        ad_view.adListener = object : AdListener(){
            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                ad_view.visibility = View.GONE
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                if (ad_view.visibility == View.GONE) ad_view.visibility = View.VISIBLE
            }
        }
        interstitialAd = InterstitialAd(this)
        interstitialAd.adUnitId = getString(R.string.banner_ad_unit_id)
        interstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                showResult()
                finish()
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                super.onAdFailedToLoad(errorCode)
                showResult()
                finish()
            }
        }
        interstitialAd.loadAd(AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build())
    }

    private fun addButton(text: String) {
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        params.topMargin = 8
        val button = Button(this)
        button.layoutParams = params
        button.setTextColor(Color.parseColor("#dfe7ff"))
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, resources.getDimension(R.dimen.answer_btn_text_size))
        button.background = ContextCompat.getDrawable(this, R.drawable.rounded_button)
        button.text = text
        button.isAllCaps = false
        buttons.add(button)
        button.id = View.generateViewId()
        answers_layout.addView(button)
    }

    private fun back() {
        if (currentQuestion != 1) {
            scalesValues[lastEditedScale] -= addedValuesStack.pop()
            currentQuestion--
            showQuestion()
        } else finish()
    }

    private fun showQuestion() {
        if (currentQuestion <= test.questionsCount!!) {
            Logger.d("Number : $currentQuestion, summary : $scalesValues, last scale : $lastEditedScale")
            test_progress_count.text = "$currentQuestion/$questionsCount"
            progress_bar.progress = currentQuestion
            val question = test.questions[currentQuestion - 1]
            question_text.text = question.questionText
            if (test.answers == null) {
                for (i in 0 until answersCount) {
                    buttons[i].text = question.answers!![i]
                }
            }
        } else {
            if (interstitialAd.isLoaded) {
                Logger.d("ad is loaded")
                interstitialAd.show()
            } else {
                Logger.d("ad isn't loaded")
                showResult()
                finish()
            }
        }

    }


    private fun initListeners() {
        val listener1 = View.OnClickListener {
            val value = when (it.id) {
                buttons[0].id -> 0
                buttons[1].id -> 1
                buttons[2].id -> 2
                buttons[3].id -> 3
                else -> throw IllegalArgumentException()
            }
            lastEditedScale = 0
            scalesValues[lastEditedScale] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener2 = View.OnClickListener {
            val value: Int = when (it.id) {
                buttons[0].id -> if (test.questions[currentQuestion - 1].right!!) 1 else 4
                buttons[1].id -> if (test.questions[currentQuestion - 1].right!!) 2 else 3
                buttons[2].id -> if (test.questions[currentQuestion - 1].right!!) 3 else 2
                buttons[3].id -> if (test.questions[currentQuestion - 1].right!!) 4 else 1
                else -> throw IllegalArgumentException("wrond id")
            }
            lastEditedScale = 0
            scalesValues[lastEditedScale] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener3 = View.OnClickListener {
            val value = when (it.id) {
                buttons[0].id -> if (test.questions[currentQuestion - 1].right!!) 1 else 7
                buttons[1].id -> if (test.questions[currentQuestion - 1].right!!) 2 else 6
                buttons[2].id -> if (test.questions[currentQuestion - 1].right!!) 3 else 5
                buttons[3].id -> 4
                buttons[4].id -> if (test.questions[currentQuestion - 1].right!!) 5 else 3
                buttons[5].id -> if (test.questions[currentQuestion - 1].right!!) 6 else 2
                buttons[6].id -> if (test.questions[currentQuestion - 1].right!!) 7 else 1
                else -> throw IllegalArgumentException("wrond id")
            }
            lastEditedScale = 0
            scalesValues[0] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener4 = View.OnClickListener {
            val value = when (it.id) {
                buttons[0].id -> if (test.questions[currentQuestion - 1].right!!) 1 else 7
                buttons[1].id -> if (test.questions[currentQuestion - 1].right!!) 2 else 6
                buttons[2].id -> if (test.questions[currentQuestion - 1].right!!) 3 else 5
                buttons[3].id -> 4
                buttons[4].id -> if (test.questions[currentQuestion - 1].right!!) 5 else 3
                buttons[5].id -> if (test.questions[currentQuestion - 1].right!!) 6 else 2
                buttons[6].id -> if (test.questions[currentQuestion - 1].right!!) 7 else 1
                else -> throw IllegalArgumentException("wrond id")
            }
            val scale = test.questions[currentQuestion - 1].scale!! - 1
            lastEditedScale = scale
            scalesValues[scale] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener5 = View.OnClickListener {
            val value = when (it.id) {
                buttons[0].id -> 1
                buttons[1].id -> 0
                else -> throw IllegalArgumentException("wrond id")
            }
            lastEditedScale = 0
            scalesValues[0] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener6 = View.OnClickListener {
            val value = when (it.id) {
                buttons[0].id -> if (test.questions[currentQuestion - 1].key!! == 1) 2 else 0
                buttons[1].id -> 1
                buttons[2].id -> if (test.questions[currentQuestion - 1].key!! == 3) 2 else 0
                else -> throw IllegalArgumentException("wrond id")
            }
            lastEditedScale = 0
            scalesValues[0] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener7 = View.OnClickListener {
            val value = when (it.id) {
                buttons[0].id -> if (test.questions[currentQuestion - 1].right!!) 1 else 0
                buttons[1].id -> if (test.questions[currentQuestion - 1].right!!) 0 else 1
                else -> throw IllegalArgumentException("wrond id")
            }
            val scale = test.questions[currentQuestion - 1].scale!! - 1
            lastEditedScale = scale
            scalesValues[scale] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener8 = View.OnClickListener {
            val key = test.questions[currentQuestion - 1].key!!
            val value = when (it.id) {
                buttons[0].id -> if (key == 1) 2 else 0
                buttons[1].id -> if (key == 1) 1 else 0
                buttons[2].id -> if (key == 0) 1 else 0
                buttons[3].id -> if (key == 0) 2 else 0
                else -> throw IllegalArgumentException("wrond id")
            }
            lastEditedScale = 0
            scalesValues[0] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val clickListener = when (test.id) {
            TestsTypes.BECK_DEPRESSION -> listener1
            TestsTypes.ZUNG_DEPRESSION, TestsTypes.BECK_HOPELESSNESS, TestsTypes.ZUNG_ANXIETY -> listener2
            TestsTypes.KARPOV_REFLECTION -> listener3
            TestsTypes.MASLACH -> listener4
            TestsTypes.BRETMEN, TestsTypes.TEST_17 -> listener5
            TestsTypes.OUB -> listener6
            TestsTypes.EPI, TestsTypes.OST, TestsTypes.BASS, TestsTypes.TEST_14, TestsTypes.TEST_15,TestsTypes.TEST_16 -> listener7
            TestsTypes.EQ -> listener8
            else -> throw Exception("wrong test id")
        }

        for (btn: Button in buttons) {
            btn.setOnClickListener(clickListener)
        }
    }


    private fun showResult() {
        val result: Result = when (scalesCount) {
            1 -> OneScaleResult(test.name!!, Date().time, test.interpretation, test.id, scalesValues[0])
            3 -> ThreeScalesResult(test.name!!, Date().time,
                    test.interpretation, test.id, scalesValues[0], scalesValues[1], scalesValues[2])
            9 -> ScaleResult(test.name!!, Date().time, test.interpretation, test.id, scalesValues)
            8 -> ScaleResult(test.name!!, Date().time, test.interpretation, test.id, scalesValues)
            else -> throw IllegalArgumentException("Unknown scales count")
        }
        if (preferenceHelper.saveResultsEnabled()) {
            dao.saveToHistory(result)
        }
        val intent = when (result) {
            is OneScaleResult -> Intent(this, OneScaleResultActivity::class.java)
                    .putExtra(OneScaleResultActivity.RESULT_DATA_PARAM, result)
            is ThreeScalesResult -> Intent(this, ThreeScalesResultActivity::class.java)
                    .putExtra(ThreeScalesResultActivity.RESULT_DATA_PARAM, result)
            is ScaleResult -> {
                if (scalesCount == 9) Intent(this, OstResultActivity::class.java)
                        .putExtra(OstResultActivity.RESULT_DATA_PARAM, result)
                else Intent(this, BassResultActivity::class.java)
                        .putExtra(BassResultActivity.RESULT_DATA_PARAM, result)
            }
            else -> throw IllegalArgumentException("wrong result type")
        }
        startActivity(intent)
    }

}
