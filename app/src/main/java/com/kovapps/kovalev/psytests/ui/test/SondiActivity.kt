package com.kovapps.kovalev.psytests.ui.test


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.ScaleResult
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.result.SondiResultActivity
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class SondiActivity : AppCompatActivity(), View.OnClickListener {


    companion object {
        private const val SAVED_VIEWS = "views"
        private const val SAVED_VECTORS = "vectors"
        private const val ANSWERS_COUNT_SAVE = "answers_count"
        private const val SERIES_SAVE = "series"
    }

    @Inject
    lateinit var dao: TestDao

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private lateinit var portraitsViews: Array<ImageView>

    private var vectors = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0)

    private var answersCount = 0

    private var series: Int = 1

    private var selectedViewsId = ArrayList<Int>(8)

    private var ad: InterstitialAd? = null

    private var adWasShowed = false
    private var adError = false

    private lateinit var backBtn: ImageView
    private lateinit var instructionText: TextView
    private lateinit var portrait1: ImageView
    private lateinit var portrait2: ImageView
    private lateinit var portrait3: ImageView
    private lateinit var portrait4: ImageView
    private lateinit var portrait5: ImageView
    private lateinit var portrait6: ImageView
    private lateinit var portrait7: ImageView
    private lateinit var portrait8: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var portraitsLayout: GridLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sondy)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        backBtn = findViewById(R.id.back_btn)
        instructionText = findViewById(R.id.temperament_value)
        portrait1 = findViewById(R.id.portrait_1)
        portrait2 = findViewById(R.id.portrait_2)
        portrait3 = findViewById(R.id.portrait_3)
        portrait4 = findViewById(R.id.portrait_4)
        portrait5 = findViewById(R.id.portrait_5)
        portrait6 = findViewById(R.id.portrait_6)
        portrait7 = findViewById(R.id.portrait_7)
        portrait8 = findViewById(R.id.portrait_8)
        progressBar = findViewById(R.id.progress_bar)
        portraitsLayout = findViewById(R.id.portraits_layout)
        portraitsViews = arrayOf(
            portrait1,
            portrait2,
            portrait3,
            portrait4,
            portrait5,
            portrait6,
            portrait7,
            portrait8
        )
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            answersCount = savedInstanceState.getInt(ANSWERS_COUNT_SAVE)
            series = savedInstanceState.getInt(SERIES_SAVE)
            selectedViewsId = savedInstanceState.getIntegerArrayList(SAVED_VIEWS)!!
            vectors = savedInstanceState.getIntegerArrayList(SAVED_VECTORS)!!
            for (id in selectedViewsId) {
                findViewById<View>(id).visibility = View.INVISIBLE
            }
        } else setAllPortraitsVisible()
        setPortraits()
        initAd()
        showProgress(true)
        backBtn.setOnClickListener { finish() }
        setPositiveInstructionText()
        showProgress(false)
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

    private fun showResult() {
        val result =
            ScaleResult(19, getString(R.string.szondi_test), Date().time, TestsTypes.SONDY, vectors)
        val intent = Intent(this, SondiResultActivity::class.java)
            .putExtra(SondiResultActivity.RESULT_DATA_PARAM, result)
        if (preferenceHelper.saveResultsEnabled()) dao.saveToHistory(result)
        dao.incrementCompletedTests(TestsTypes.SONDY)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntegerArrayList(SAVED_VIEWS, selectedViewsId)
        outState.putIntegerArrayList(SAVED_VECTORS, ArrayList(vectors))
        outState.putInt(SERIES_SAVE, series)
        outState.putInt(ANSWERS_COUNT_SAVE, answersCount)
    }

    private fun setPortraits() {
        val resource1: Int = when (series) {
            1 -> R.drawable.portrait1
            2 -> R.drawable.portrait9
            3 -> R.drawable.portrait17
            4 -> R.drawable.portrait25
            5 -> R.drawable.portrait33
            6 -> R.drawable.portrait41
            else -> throw IllegalArgumentException("Unexpected series")
        }
        val resource2 = when (series) {
            1 -> R.drawable.portrait2
            2 -> R.drawable.portrait10
            3 -> R.drawable.portrait18
            4 -> R.drawable.portrait26
            5 -> R.drawable.portrait34
            6 -> R.drawable.portrait42
            else -> throw IllegalArgumentException("Unexpected series")
        }
        val resource3 = when (series) {
            1 -> R.drawable.portrait3
            2 -> R.drawable.portrait11
            3 -> R.drawable.portrait19
            4 -> R.drawable.portrait27
            5 -> R.drawable.portrait35
            6 -> R.drawable.portrait43
            else -> throw IllegalArgumentException("Unexpected series")
        }
        val resource4 = when (series) {
            1 -> R.drawable.portrait4
            2 -> R.drawable.portrait12
            3 -> R.drawable.portrait20
            4 -> R.drawable.portrait28
            5 -> R.drawable.portrait36
            6 -> R.drawable.portrait44
            else -> throw IllegalArgumentException("Unexpected series")
        }
        val resource5 = when (series) {
            1 -> R.drawable.portrait5
            2 -> R.drawable.portrait13
            3 -> R.drawable.portrait21
            4 -> R.drawable.portrait29
            5 -> R.drawable.portrait37
            6 -> R.drawable.portrait45
            else -> throw IllegalArgumentException("Unexpected series")
        }
        val resource6 = when (series) {
            1 -> R.drawable.portrait6
            2 -> R.drawable.portrait14
            3 -> R.drawable.portrait22
            4 -> R.drawable.portrait30
            5 -> R.drawable.portrait38
            6 -> R.drawable.portrait46
            else -> throw IllegalArgumentException("Unexpected series")
        }
        val resource7 = when (series) {
            1 -> R.drawable.portrait7
            2 -> R.drawable.portrait15
            3 -> R.drawable.portrait23
            4 -> R.drawable.portrait31
            5 -> R.drawable.portrait39
            6 -> R.drawable.portrait47
            else -> throw  IllegalArgumentException("Unexpected series")
        }
        val resource8 = when (series) {
            1 -> R.drawable.portrait8
            2 -> R.drawable.portrait16
            3 -> R.drawable.portrait24
            4 -> R.drawable.portrait32
            5 -> R.drawable.portrait40
            6 -> R.drawable.portrait48
            else -> throw IllegalArgumentException("Unexpected series")
        }
        portraitsViews.forEach { it.setOnClickListener(this) }
        portrait1.setImageDrawable(ContextCompat.getDrawable(this, resource1))
        portrait2.setImageDrawable(ContextCompat.getDrawable(this, resource2))
        portrait3.setImageDrawable(ContextCompat.getDrawable(this, resource3))
        portrait4.setImageDrawable(ContextCompat.getDrawable(this, resource4))
        portrait5.setImageDrawable(ContextCompat.getDrawable(this, resource5))
        portrait6.setImageDrawable(ContextCompat.getDrawable(this, resource6))
        portrait7.setImageDrawable(ContextCompat.getDrawable(this, resource7))
        portrait8.setImageDrawable(ContextCompat.getDrawable(this, resource8))
    }

    override fun onClick(v: View) {
        answersCount++
        val pos = answersCount <= 1
        when (v.id) {
            R.id.portrait_1 -> if (pos) vectors[0]++ else vectors[0]--
            R.id.portrait_2 -> if (pos) vectors[1]++ else vectors[1]--
            R.id.portrait_3 -> if (pos) vectors[2]++ else vectors[2]--
            R.id.portrait_4 -> if (pos) vectors[3]++ else vectors[3]--
            R.id.portrait_5 -> if (pos) vectors[4]++ else vectors[4]--
            R.id.portrait_6 -> if (pos) vectors[5]++ else vectors[5]--
            R.id.portrait_7 -> if (pos) vectors[6]++ else vectors[6]--
            R.id.portrait_8 -> if (pos) vectors[7]++ else vectors[7]--
        }
        v.visibility = View.INVISIBLE
        selectedViewsId.add(v.id)
        if (!pos) setNegativeInstructionText()
        else setPositiveInstructionText()
        if (answersCount == 4) {
            series++
            answersCount = 0
            if (series > 6) {
                showResult()
            } else {
                selectedViewsId.clear()
                setAllPortraitsVisible()
                setPortraits()
                setPositiveInstructionText()
            }

        }
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
            portraitsLayout.visibility = View.INVISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
            portraitsLayout.visibility = View.VISIBLE
        }
    }

    private fun setAllPortraitsVisible() = portraitsViews.forEach { it.visibility = View.VISIBLE }


    private fun setPositiveInstructionText() {
        val span = getString(R.string.szondi_positive_instruction)
        instructionText.text = span
    }

    private fun setNegativeInstructionText() {
        val span = getString(R.string.szondi_negative_instruction)
        instructionText.text = span
    }

}
