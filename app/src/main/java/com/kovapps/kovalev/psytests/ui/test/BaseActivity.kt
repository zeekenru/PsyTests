package com.kovapps.kovalev.psytests.ui.test

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.*
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.result.*
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class BaseActivity : AppCompatActivity() {

    companion object {
        const val TEST_PARAM = "test"
        private const val VALUES_RESTORE_KEY = "values"
        private const val QS_COUNT_SAVE = "qs_count"
        private const val CURRENT_QUESTION_SAVE = "CQ"
        private const val TEST_SAVE = "test_save"
        private const val SCALES_COUNT_SAVE = "scales_count"
        private const val LAST_EDITED_SCALE_SAVE = "LD"
        private const val ANSWERS_COUNT_SAVE = "answers_count"
    }


    private var questionsCount: Int = 0

    private var currentQuestion: Int = 1

    private val scalesValues = mutableListOf<Int>()

    private lateinit var test: PsyTest

    private var scalesCount = 0

    private var lastEditedScale = -1

    private var answersCount = 0

    private val buttons = mutableListOf<Button>()

    private val addedValuesStack = Stack<Int>()

    private var isRestoredState = false

    private var ad: InterstitialAd? = null

    private var adWasShowed = false
    private var adError = false

    private lateinit var progressBar: ProgressBar

    private lateinit var closeBtn: ImageView

    private lateinit var answerLayout: LinearLayout
    private lateinit var scrollView: ScrollView
    private lateinit var testProgressCount: TextView
    private lateinit var questionText: TextView
    private lateinit var backBtn: View

    private var maxButtonHeight = -10

    @Inject
    lateinit var dao: TestDao

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        progressBar = findViewById(R.id.progress_bar)
        closeBtn = findViewById(R.id.close_navigation_btn)
        answerLayout = findViewById(R.id.answers_layout)
        scrollView = findViewById(R.id.root_scroll_view)
        testProgressCount = findViewById(R.id.test_progress_count)
        questionText = findViewById(R.id.question_text)
        backBtn = findViewById(R.id.back_btn)
        backBtn.setOnClickListener {
            back()
        }

        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            isRestoredState = true
            with(savedInstanceState) {
                answersCount = getInt(ANSWERS_COUNT_SAVE)
                scalesCount = getInt(SCALES_COUNT_SAVE)
                test = getParcelable(TEST_SAVE)!!
                currentQuestion = getInt(CURRENT_QUESTION_SAVE)
                lastEditedScale = getInt(LAST_EDITED_SCALE_SAVE)
                questionsCount = getInt(CURRENT_QUESTION_SAVE)
            }
            scalesValues.addAll(savedInstanceState.getIntegerArrayList(VALUES_RESTORE_KEY)!!)
        } else {
            test = intent.getParcelableExtra(TEST_PARAM)!!
            scalesCount = test.scalesCount
        }
        questionsCount = test.questions!!.size
        for (i in 0 until scalesCount) {
            if (!isRestoredState) scalesValues.add(i, 0)
        }

        progressBar.max = questionsCount
        progressBar.progressDrawable.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                ContextCompat.getColor(
                    this,
                    R.color.primaryColor
                ), BlendModeCompat.SRC_IN
            )
        closeBtn.setOnClickListener { finish() }
        if (test.answers != null) {
            answersCount = test.answers!!.size
            for (i in 0 until answersCount) {
                addButton(test.answers!![i])
            }
        } else {
            answersCount = test.questions!![currentQuestion].answers!!.size
            for (i in 0 until answersCount) {
                addButton(test.questions!![currentQuestion - 1].answers!![i])
            }
        }
        initAd()
        initListeners()
        showQuestion()
    }

    private fun initAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, getString(R.string.full_screen_ad_id), adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad1: InterstitialAd) {
                    super.onAdLoaded(ad1)
                    this@BaseActivity.ad = ad1
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    ad = null
                }
            })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putInt(QS_COUNT_SAVE, questionsCount)
            putInt(ANSWERS_COUNT_SAVE, answersCount)
            putInt(CURRENT_QUESTION_SAVE, currentQuestion)
            putInt(LAST_EDITED_SCALE_SAVE, lastEditedScale)
            putInt(SCALES_COUNT_SAVE, scalesCount)
            putIntegerArrayList(VALUES_RESTORE_KEY, ArrayList(scalesValues))
            putParcelable(TEST_SAVE, test)
        }
    }

    private fun addButton(text: String) {
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.topMargin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            8F,
            this.resources.displayMetrics
        ).toInt()
        val button = Button(this)
        with(button) {
            layoutParams = params
            val padding = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                8F,
                context.resources.displayMetrics
            ).toInt()
            setPadding(padding, padding, padding, padding)
            setTextColor(Color.parseColor("#dfe7ff"))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
            textAlignment = Button.TEXT_ALIGNMENT_CENTER
//            background = ContextCompat.getDrawable(this@BaseActivity, R.drawable.rounded_button)
            background =
                ContextCompat.getDrawable(this@BaseActivity, R.drawable.base_test_btn_selector)
            this.text = text
            isAllCaps = false
            id = View.generateViewId()
        }
        button.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View?,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                if (v!!.width > 0) v.removeOnLayoutChangeListener(this)
                if (v.height > maxButtonHeight) {
                    maxButtonHeight = v.height
                } else {
                    val params1 = LinearLayout.LayoutParams(v.width, maxButtonHeight)
                    params1.topMargin = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        8F,
                        v.context.resources.displayMetrics
                    ).toInt()
                    v.layoutParams = params
                }
            }
        })
        buttons.add(button)
        answerLayout.addView(button)
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
            scrollView.fullScroll(ScrollView.FOCUS_UP)
            testProgressCount.text = "$currentQuestion/$questionsCount"
            progressBar.progress = currentQuestion
            val question = test.questions!![currentQuestion - 1]
            questionText.text = question.questionText.replaceFirstChar { it.uppercase() }
            if (test.answers == null) {
                for (i in 0 until answersCount) {
                    buttons[i].text = question.answers!![i].replaceFirstChar { it.uppercase() }
                }
            }
        } else {
            showResult()
        }

    }

    override fun onBackPressed() {
        if (currentQuestion != -1) back()
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
                buttons[0].id -> if (test.questions!![currentQuestion - 1].right!!) 1 else 4
                buttons[1].id -> if (test.questions!![currentQuestion - 1].right!!) 2 else 3
                buttons[2].id -> if (test.questions!![currentQuestion - 1].right!!) 3 else 2
                buttons[3].id -> if (test.questions!![currentQuestion - 1].right!!) 4 else 1
                else -> throw IllegalArgumentException("wrong id")
            }
            lastEditedScale = 0
            scalesValues[lastEditedScale] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener3 = View.OnClickListener {
            val value = when (it.id) {
                buttons[0].id -> if (test.questions!![currentQuestion - 1].right!!) 1 else 7
                buttons[1].id -> if (test.questions!![currentQuestion - 1].right!!) 2 else 6
                buttons[2].id -> if (test.questions!![currentQuestion - 1].right!!) 3 else 5
                buttons[3].id -> 4
                buttons[4].id -> if (test.questions!![currentQuestion - 1].right!!) 5 else 3
                buttons[5].id -> if (test.questions!![currentQuestion - 1].right!!) 6 else 2
                buttons[6].id -> if (test.questions!![currentQuestion - 1].right!!) 7 else 1
                else -> throw IllegalArgumentException("wrong id")
            }
            lastEditedScale = 0
            scalesValues[0] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener4 = View.OnClickListener {
            val value = when (it.id) {
                buttons[0].id -> if (test.questions!![currentQuestion - 1].right!!) 1 else 7
                buttons[1].id -> if (test.questions!![currentQuestion - 1].right!!) 2 else 6
                buttons[2].id -> if (test.questions!![currentQuestion - 1].right!!) 3 else 5
                buttons[3].id -> 4
                buttons[4].id -> if (test.questions!![currentQuestion - 1].right!!) 5 else 3
                buttons[5].id -> if (test.questions!![currentQuestion - 1].right!!) 6 else 2
                buttons[6].id -> if (test.questions!![currentQuestion - 1].right!!) 7 else 1
                else -> throw IllegalArgumentException("wrong id")
            }
            val scale = test.questions!![currentQuestion - 1].scale!! - 1
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
                else -> throw IllegalArgumentException("wrong id")
            }
            lastEditedScale = 0
            scalesValues[0] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener6 = View.OnClickListener {
            val value = when (it.id) {
                buttons[0].id -> if (test.questions!![currentQuestion - 1].key!! == 1) 2 else 0
                buttons[1].id -> 1
                buttons[2].id -> if (test.questions!![currentQuestion - 1].key!! == 3) 2 else 0
                else -> throw IllegalArgumentException("wrong id")
            }
            lastEditedScale = 0
            scalesValues[0] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener7 = View.OnClickListener {
            val value = when (it.id) {
                buttons[0].id -> if (test.questions!![currentQuestion - 1].right!!) 1 else 0
                buttons[1].id -> if (test.questions!![currentQuestion - 1].right!!) 0 else 1
                else -> throw IllegalArgumentException("wrong id")
            }
            val scale = test.questions!![currentQuestion - 1].scale!! - 1
            lastEditedScale = scale
            scalesValues[scale] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener8 = View.OnClickListener {
            val key = test.questions!![currentQuestion - 1].key!!
            val value = when (it.id) {
                buttons[0].id -> if (key == 1) 2 else 0
                buttons[1].id -> if (key == 1) 1 else 0
                buttons[2].id -> if (key == 0) 1 else 0
                buttons[3].id -> if (key == 0) 2 else 0
                else -> throw IllegalArgumentException("wrong id")
            }
            lastEditedScale = 0
            scalesValues[0] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener9 = View.OnClickListener {
            val value = when (it.id) {
                buttons[0].id -> if (test.questions!![currentQuestion - 1].right!!) 1 else 5
                buttons[1].id -> if (test.questions!![currentQuestion - 1].right!!) 2 else 4
                buttons[2].id -> 3
                buttons[3].id -> if (test.questions!![currentQuestion - 1].right!!) 4 else 2
                buttons[4].id -> if (test.questions!![currentQuestion - 1].right!!) 5 else 1
                else -> throw IllegalArgumentException("wrong id")
            }
            val scale = test.questions!![currentQuestion - 1].scale!! - 1
            lastEditedScale = scale
            scalesValues[scale] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener10 = View.OnClickListener {
            val value = when (it.id) {
                buttons[0].id -> if (test.questions!![currentQuestion - 1].right!!) 0 else 5
                buttons[1].id -> if (test.questions!![currentQuestion - 1].right!!) 1 else 4
                buttons[2].id -> if (test.questions!![currentQuestion - 1].right!!) 2 else 3
                buttons[3].id -> if (test.questions!![currentQuestion - 1].right!!) 3 else 2
                buttons[4].id -> if (test.questions!![currentQuestion - 1].right!!) 4 else 1
                buttons[5].id -> if (test.questions!![currentQuestion - 1].right!!) 5 else 0
                else -> throw IllegalArgumentException("wrong id")
            }
            val scale = test.questions!![currentQuestion - 1].scale!! - 1
            lastEditedScale = scale
            scalesValues[scale] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener11 = View.OnClickListener {
            val value = when (it.id) {
                buttons[0].id -> 0
                buttons[1].id -> 1
                buttons[2].id -> 2
                buttons[3].id -> 3
                buttons[4].id -> 4
                else -> throw IllegalArgumentException("wrong id")
            }
            val scale = test.questions!![currentQuestion - 1].scale!! - 1
            lastEditedScale = scale
            scalesValues[scale] += value
            addedValuesStack.push(value)
            currentQuestion++
            showQuestion()
        }
        val listener12 = View.OnClickListener {
            val value: Int = when (it.id) {
                buttons[0].id -> if (test.questions!![currentQuestion - 1].right!!) 1 else 4
                buttons[1].id -> if (test.questions!![currentQuestion - 1].right!!) 2 else 3
                buttons[2].id -> if (test.questions!![currentQuestion - 1].right!!) 3 else 2
                buttons[3].id -> if (test.questions!![currentQuestion - 1].right!!) 4 else 1
                else -> throw IllegalArgumentException("wrong id")
            }
            val scale = test.questions!![currentQuestion - 1].scale!! - 1
            lastEditedScale = scale
            scalesValues[lastEditedScale] += value
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
            TestsTypes.EPI, TestsTypes.OST, TestsTypes.BASS, TestsTypes.TEST_14, TestsTypes.TEST_15, TestsTypes.TEST_16 -> listener7
            TestsTypes.EQ -> listener8
            TestsTypes.TEST_18 -> listener9
            TestsTypes.TEST_20 -> listener10
            TestsTypes.TEST_21 -> listener11
            TestsTypes.TEST_22 -> listener12
            else -> throw Exception("wrong test id")
        }

        for (btn: Button in buttons) {
            btn.setOnClickListener(clickListener)
        }
    }


    private fun showResult() {
        val result: HistoryItem = when (scalesCount) {
            1 -> OneScaleResult(test.id, test.name, Date().time, scalesValues[0])
            3 -> ThreeScalesResult(
                test.id, test.name, Date().time,
                scalesValues[0], scalesValues[1], scalesValues[2]
            )
            8, 9, 5, 10 -> ScaleResult(
                test.id,
                test.name, Date().time, test.id, scalesValues
            )
            else -> throw IllegalArgumentException("Unknown scales count")
        }
        if (preferenceHelper.saveResultsEnabled()) {
            dao.saveToHistory(result)
        }
        val intent = when (scalesCount) {
            1 -> Intent(this, OneScaleResultActivity::class.java)
                .putExtra(OneScaleResultActivity.RESULT_DATA_PARAM, result as OneScaleResult)
            3 -> Intent(this, ThreeScalesResultActivity::class.java)
                .putExtra(ThreeScalesResultActivity.RESULT_DATA_PARAM, result as ThreeScalesResult)
            9 -> {
                when (test.id) {
                    TestsTypes.TEST_20 -> Intent(this, Test20ResultActivity::class.java)
                        .putExtra(Test20ResultActivity.RESULT_DATA_PARAM, result as ScaleResult)
                    TestsTypes.OST -> Intent(this, OstResultActivity::class.java)
                        .putExtra(OstResultActivity.RESULT_DATA_PARAM, result as ScaleResult)
                    else -> throw IllegalArgumentException("Unknown scales count")
                }
            }
            8 -> Intent(this, BassResultActivity::class.java)
                .putExtra(BassResultActivity.RESULT_DATA_PARAM, result as ScaleResult)
            5 -> Intent(this, FiveScalesResultActivity::class.java)
                .putExtra(FiveScalesResultActivity.RESULT_DATA_PARAM, result as ScaleResult)
            10 -> Intent(this, Test22ResultActivity::class.java)
                .putExtra(Test22ResultActivity.RESULT_DATA_PARAM, result as ScaleResult)
            else -> throw IllegalArgumentException("Unknown scales count")

        }
        dao.incrementCompletedTests(test.id)
        if (ad != null && !adError) {
            ad!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    adError = true
                    startActivity(intent)
                    finish()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    adWasShowed = true
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    startActivity(intent)
                    finish()
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    adWasShowed = true

                }
            }
            ad!!.show(this)
        } else {
            startActivity(intent)
            finish()
        }

    }

}
