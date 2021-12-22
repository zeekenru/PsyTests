package com.kovapps.kovalev.psytests.ui.test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.LuscherResult
import com.kovapps.kovalev.psytests.enities.LusherColor
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.result.LuscherResultActivity
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject

class LusherActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val SAVED_VIEWS = "views"
        private const val FIRST_ANSWERS = "first_answers"
        private const val SECOND_ANSWERS = "second_answers"
        private const val COLORS = "colors"
        private const val CARDS_COUNT = 8
        private const val IS_FIRST_STATE_SAVE = "is_first"
        private const val ANSWERS_COUNT_SAVE = "answers_count"
        private const val IS_PAUSE_SAVE = "is_pause"

    }

    @Inject
    lateinit var dao: TestDao

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private lateinit var views: List<View>
    private val colors = LusherColor.values().toMutableList()
    private var firstAnswers = ArrayList<LusherColor>(CARDS_COUNT)
    private var secondAnswers = ArrayList<LusherColor>(CARDS_COUNT)
    private var selectedViewsId = ArrayList<Int>(CARDS_COUNT)
    private var isFirstState: Boolean = true
    private var answersCount: Int = 0
    private var isPause: Boolean = false
    private var ad: InterstitialAd? = null
    private var adError = false
    private var adWasShowed = false
    private lateinit var lastSelectedColorView: View


    private lateinit var backBtn: ImageView
    private lateinit var instructionText: TextView
    private lateinit var colorCardsLayout: GridLayout
    private lateinit var luscherPauseLayout: View
    private lateinit var continueBtn: AppCompatButton
    private lateinit var closeBtn: ImageView

    private lateinit var luscherColor1: View
    private lateinit var luscherColor2: View
    private lateinit var luscherColor3: View
    private lateinit var luscherColor4: View
    private lateinit var luscherColor5: View
    private lateinit var luscherColor6: View
    private lateinit var luscherColor7: View
    private lateinit var luscherColor8: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_luscher)
        backBtn = findViewById(R.id.back_btn)
        instructionText = findViewById(R.id.temperament_value)
        colorCardsLayout = findViewById(R.id.colors_cards_layout)
        luscherPauseLayout = findViewById(R.id.luscher_pause_layout)
        continueBtn = luscherPauseLayout.findViewById(R.id.continue_btn)
        closeBtn = luscherPauseLayout.findViewById(R.id.close_btn)

        luscherColor1 = findViewById(R.id.lusher_color_1)
        luscherColor2 = findViewById(R.id.lusher_color_2)
        luscherColor3 = findViewById(R.id.lusher_color_3)
        luscherColor4 = findViewById(R.id.lusher_color_4)
        luscherColor5 = findViewById(R.id.lusher_color_5)
        luscherColor6 = findViewById(R.id.lusher_color_6)
        luscherColor7 = findViewById(R.id.lusher_color_7)
        luscherColor8 = findViewById(R.id.lusher_color_8)

        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        initAd()
        views = listOf(
            luscherColor1,
            luscherColor2,
            luscherColor3,
            luscherColor4,
            luscherColor5,
            luscherColor6,
            luscherColor7,
            luscherColor8
        )
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            isFirstState = savedInstanceState.getBoolean(IS_FIRST_STATE_SAVE)
            answersCount = savedInstanceState.getInt(ANSWERS_COUNT_SAVE)
            isPause = savedInstanceState.getBoolean(IS_PAUSE_SAVE)
            firstAnswers = savedInstanceState.getParcelableArrayList(FIRST_ANSWERS)!!
            secondAnswers = savedInstanceState.getParcelableArrayList(SECOND_ANSWERS)!!
            selectedViewsId = savedInstanceState.getIntegerArrayList(SAVED_VIEWS)!!
            if (isPause) showPauseScreen()
            else {
                colors.clear()
                colors.addAll(savedInstanceState.getParcelableArrayList(COLORS)!!)
                paintViews()
                for (id in selectedViewsId) {
                    findViewById<View>(id).visibility = View.INVISIBLE
                }
            }
        } else {
            paintViews()
        }
        views.forEachIndexed { index, view ->
            view.setOnClickListener(this)
        }
        backBtn.setOnClickListener {
            finish()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntegerArrayList(SAVED_VIEWS, selectedViewsId)
        outState.putParcelableArrayList(FIRST_ANSWERS, firstAnswers)
        outState.putParcelableArrayList(SECOND_ANSWERS, secondAnswers)
        outState.putParcelableArrayList(COLORS, ArrayList(colors))
        outState.putBoolean(IS_PAUSE_SAVE, isPause)
        outState.putBoolean(IS_FIRST_STATE_SAVE, isFirstState)
        outState.putInt(ANSWERS_COUNT_SAVE, answersCount)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.lusher_color_1 -> {
                luscherColor1.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[0]) else secondAnswers.add(colors[0])
                answersCount++
                lastSelectedColorView = luscherColor1
            }
            R.id.lusher_color_2 -> {
                luscherColor2.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[1]) else secondAnswers.add(colors[1])
                answersCount++
                lastSelectedColorView = luscherColor3
            }
            R.id.lusher_color_3 -> {
                luscherColor3.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[2]) else secondAnswers.add(colors[2])
                answersCount++
                lastSelectedColorView = luscherColor4
            }
            R.id.lusher_color_4 -> {
                luscherColor4.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[3]) else secondAnswers.add(colors[3])
                answersCount++
                lastSelectedColorView = luscherColor4
            }
            R.id.lusher_color_5 -> {
                luscherColor5.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[4]) else secondAnswers.add(colors[4])
                answersCount++
                lastSelectedColorView = luscherColor5
            }
            R.id.lusher_color_6 -> {
                luscherColor6.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[5]) else secondAnswers.add(colors[5])
                answersCount++
                lastSelectedColorView = luscherColor6
            }
            R.id.lusher_color_7 -> {
                luscherColor7.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[6]) else secondAnswers.add(colors[6])
                answersCount++
                lastSelectedColorView = luscherColor7
            }
            R.id.lusher_color_8 -> {
                luscherColor8.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[7]) else secondAnswers.add(colors[7])
                answersCount++
                lastSelectedColorView = luscherColor8
            }
        }
        selectedViewsId.add(v.id)
        if (answersCount == 8) {
            if (isFirstState) {
                showPauseScreen()
            } else showResult()
        }
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

    private fun randomizeColors() = colors.shuffle()

    private fun showResult() {
        val result =
            LuscherResult(
                date = Date().time, answers = secondAnswers, testId = 10, testName = getString(
                    R.string.luscher_test
                )
            )
        if (preferenceHelper.saveResultsEnabled()) {
            dao.saveToHistory(result)
        }
        val intent = Intent(this, LuscherResultActivity::class.java)
            .putExtra(LuscherResultActivity.RESULT_DATA_PARAM, result)
        dao.incrementCompletedTests(TestsTypes.LUSHER)
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

    private fun paintViews() {
        views.forEachIndexed { index, view ->
            view.setBackgroundColor(colors[index].color)
            view.visibility = View.VISIBLE
        }
    }

    private fun showPauseScreen() {
        isPause = true
        backBtn.visibility = View.INVISIBLE
        instructionText.visibility = View.INVISIBLE
        colorCardsLayout.visibility = View.INVISIBLE
        luscherPauseLayout.visibility = View.VISIBLE
        continueBtn.setOnClickListener {
            showSecondColorList()
        }
        closeBtn.setOnClickListener { finish() }
    }

    private fun showSecondColorList() {
        isPause = false
        selectedViewsId.clear()
        backBtn.visibility = View.VISIBLE
        instructionText.visibility = View.VISIBLE
        colorCardsLayout.visibility = View.VISIBLE
        luscherPauseLayout.visibility = View.GONE
        closeBtn.setOnClickListener { finish() }
        isFirstState = false
        answersCount = 0
        colors.shuffle()
        paintViews()
    }


}
