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
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.SimpleTest
import com.kovapps.kovalev.psytests.enities.SimpleTestResult
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.result.SimpleTestResultActivity
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject

class SimpleTestActivity : AppCompatActivity() {

    companion object {
        const val TEST_PARAM = "test"
        private const val QS_COUNT_SAVE = "qs_count"
        private const val CURRENT_QUESTION_SAVE = "CQ"
        private const val TEST_SAVE = "test_save"
        private const val POINTS_VALUE_SAVE = "points_save"
        private const val ANSWERS_COUNT_SAVE = "answers_count"
    }


    private var questionsCount: Int = 0

    private var currentQuestion: Int = 0

    private lateinit var test: SimpleTest

    private var answersCount = 0

    private val buttons = mutableListOf<Button>()

    private var pointsValue = 0

    private var isRestoredState = false

    private lateinit var progressBar: ProgressBar

    private lateinit var closeBtn: ImageView

    private lateinit var answerLayout: LinearLayout
    private lateinit var scrollView: ScrollView
    private lateinit var testProgressCount: TextView
    private lateinit var questionText: TextView
    private lateinit var backBtn: View

    private var maxButtonHeight = -10

    private var ad: InterstitialAd? = null
    private var adError = false

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
                test = getParcelable(TEST_SAVE)!!
                pointsValue = getInt(POINTS_VALUE_SAVE)
                currentQuestion = getInt(CURRENT_QUESTION_SAVE)
                questionsCount = getInt(CURRENT_QUESTION_SAVE)
            }
        } else {
            test = intent.getParcelableExtra(BaseActivity.TEST_PARAM)!!
        }
        questionsCount = test.questions.size
        progressBar.max = questionsCount
        progressBar.progressDrawable.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                ContextCompat.getColor(this, R.color.primaryColor), BlendModeCompat.SRC_IN
            )
        closeBtn.setOnClickListener { finish() }
        initAd()
        showQuestion()
        initListener()
    }

    private fun initAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, getString(R.string.full_screen_ad_id), adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad1: InterstitialAd) {
                    super.onAdLoaded(ad1)
                    ad = ad1
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    ad = null
                }
            })
    }

    private fun initListener() {
        if (currentQuestion <= questionsCount) {
            for (i in 0 until buttons.size) {
                buttons[i].setOnClickListener {
                    pointsValue += test.questions[currentQuestion].answers[i].points
                    currentQuestion++
                    showQuestion()
                }
            }
        } else {
            showResult()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putInt(QS_COUNT_SAVE, questionsCount)
            putInt(ANSWERS_COUNT_SAVE, answersCount)
            putInt(CURRENT_QUESTION_SAVE, currentQuestion)
            putInt(POINTS_VALUE_SAVE, pointsValue)
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
            setTextColor(Color.parseColor("#dfe7ff"))
            val padding = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                8F,
                context.resources.displayMetrics
            ).toInt()
            setPadding(padding, padding, padding, padding)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
            textAlignment = Button.TEXT_ALIGNMENT_CENTER
            background = ContextCompat.getDrawable(
                this@SimpleTestActivity,
                R.drawable.base_test_btn_selector
            )
            this.text = text
            isAllCaps = false
            id = View.generateViewId()
        }
        buttons.add(button)
        answerLayout.addView(button)
    }

    private fun back() {
        if (currentQuestion != 0) {
            currentQuestion--
            showQuestion()
        } else finish()
    }

    private fun showQuestion() {
        if (currentQuestion < test.questions.size) {
            buttons.forEach {
                answerLayout.removeView(it)
            }
            buttons.clear()
            answersCount = test.questions[currentQuestion].answers.size
            for (i in 0 until answersCount) {
                addButton(test.questions[currentQuestion].answers[i].text)
            }
            initListener()
            scrollView.fullScroll(ScrollView.FOCUS_UP)
            testProgressCount.text = "${currentQuestion + 1}/$questionsCount"
            progressBar.progress = currentQuestion
            val question = test.questions[currentQuestion]
            questionText.text = question.text.replaceFirstChar { it.uppercase() }
            for (i in 0 until answersCount) {
                buttons[i].text = question.answers[i].text.replaceFirstChar { it.uppercase() }
                buttons[i].addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
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
                            val params = LinearLayout.LayoutParams(v.width, maxButtonHeight)
                            params.topMargin = TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                8F,
                                v.context.resources.displayMetrics
                            ).toInt()
                            v.layoutParams = params
                        }
                    }
                })
            }

        } else {
            showResult()
        }

    }

    override fun onBackPressed() {
        if (currentQuestion != -1) back()
    }

    private fun showResult() {
        buttons.forEach { it.isEnabled = false }
        val result = SimpleTestResult(test.id, test.name, Date().time, pointsValue, test.results)
        if (preferenceHelper.saveResultsEnabled()) {
            dao.saveToHistory(result)
        }
        val intent = Intent(this, SimpleTestResultActivity::class.java)
        intent.putExtra(SimpleTestResultActivity.RESULT_DATA_PARAM, result)
        dao.incrementCompletedTests(test.id)
        if (ad != null && !adError) {
            ad!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    adError = true
                    startActivity(intent)
                    finish()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    startActivity(intent)
                    finish()
                }

            }
            ad!!.show(this)
        } else {
            startActivity(intent)
            finish()
        }
    }

}