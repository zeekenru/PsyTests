package com.kovapps.kovalev.psytests.ui.test

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ScrollView
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.kovapps.kovalev.psytests.*
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

class BeckDepressionActivity : AppCompatActivity(), View.OnClickListener {

    @Inject lateinit var preferenceHelper: PreferenceHelper

    @Inject lateinit var dao: TestDao

    private var firstBackClick: Boolean by serialState(true)

    private var questionsCount: Int by serialState(0)

    private var currentQuestion: Int by serialState(1)

    private var summary: Int by serialState(0)

    private var lastAddedValue: Int by serialState(0)

    private var test: Test by parcelLateState()

    private lateinit var interstitialAd: InterstitialAd


    companion object {
        const val TEST_PARAM = "test"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beck_depression)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            unfreezeInstanceState(savedInstanceState)
        } else {
            test = intent.getParcelableExtra(TEST_PARAM)
        }
        if (ad_view != null && resources.getBoolean(R.bool.isTablet)){
            Logger.d("show ad view")
            ad_view!!.loadAd(AdRequest.Builder().build())
        } else Logger.d("ad view is null")
        prepareAdView()
        questionsCount = test.questions.size
        progress_bar.max = questionsCount
        progress_bar.progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.primaryColor),
                android.graphics.PorterDuff.Mode.SRC_IN)
        answer_btn_1.setOnClickListener(this)
        answer_btn_2.setOnClickListener(this)
        answer_btn_3.setOnClickListener(this)
        answer_btn_4.setOnClickListener(this)
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

    private fun prepareAdView() {
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
                when (errorCode){
                    AdRequest.ERROR_CODE_NETWORK_ERROR -> {
                        showResult()
                        finish()
                    }
                }
            }
        }
        interstitialAd.loadAd(AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build())
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
            showBackToast()
        }
    }

    private fun showQuestion() {
        if (currentQuestion <= questionsCount) {
            root_scroll_view.fullScroll(ScrollView.FOCUS_UP)
            Logger.d("show question : $currentQuestion")
            test_progress_count.text = "$currentQuestion/$questionsCount"
            progress_bar.progress = currentQuestion
            val question = test.questions[currentQuestion - 1]
            question_text.text = question.questionText
            question.answers!!
            answer_btn_1.text = question.answers[0]
            answer_btn_2.text = question.answers[1]
            answer_btn_3.text = question.answers[2]
            answer_btn_4.text = question.answers[3]
        } else {
            if (interstitialAd.isLoaded){
                Logger.d("ad is loaded")
                interstitialAd.show()
            } else {
                Logger.d("ad isn't loaded")
                showResult()
                finish()
            }


        }
    }

    private fun showResult() {
        val result = OneScaleResult(test.name!!, Date().time, test.interpretation, TestsTypes.BECK_DEPRESSION, summary )
        if (preferenceHelper.saveResultsEnabled()){
            dao.saveToHistory(result)
        }
        val intent = Intent(this, OneScaleResultActivity::class.java)
                .putExtra(OneScaleResultActivity.RESULT_DATA_PARAM, result)
        startActivity(intent)
    }

    private fun showBackToast() {
        Toast.makeText(this@BeckDepressionActivity, getString(R.string.double_click_to_close),
                Toast.LENGTH_SHORT).show()
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.answer_btn_1 -> lastAddedValue = 0

            R.id.answer_btn_2 -> {
                summary += 1
                lastAddedValue = 1
            }
            R.id.answer_btn_3 -> {
                summary += 2
                lastAddedValue = 2
            }
            R.id.answer_btn_4 -> {
                summary += 3
                lastAddedValue = 3
            }
        }
        currentQuestion++
        showQuestion()
    }
}
