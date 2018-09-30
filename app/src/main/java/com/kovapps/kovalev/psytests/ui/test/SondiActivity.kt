package com.kovapps.kovalev.psytests.ui.test

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.ScaleResult
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.result.SondiResultActivity
import com.tinsuke.icekick.extension.freezeInstanceState
import com.tinsuke.icekick.extension.serialState
import com.tinsuke.icekick.extension.unfreezeInstanceState
import kotlinx.android.synthetic.main.activity_sondy.*
import ru.noties.markwon.SpannableBuilder
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class SondiActivity : AppCompatActivity(), View.OnClickListener {


    companion object {
        private const val SAVED_VIEWS = "views"
        private const val SAVED_VECTORS = "vectors"
    }

    @Inject
    lateinit var dao: TestDao

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private lateinit var portraitsViews: Array<ImageView>

    private var vectors = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0)

    private var answersCount: Int by serialState(0)

    private var series: Int by serialState(1)

    private var selectedViewsId = ArrayList<Int>(8)

    private lateinit var interstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sondy)
        unfreezeInstanceState(savedInstanceState)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        portraitsViews = arrayOf(portrait_1, portrait_2, portrait_3, portrait_4, portrait_5,
                portrait_6, portrait_7, portrait_8)
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            selectedViewsId = savedInstanceState.getIntegerArrayList(SAVED_VIEWS)!!
            vectors = savedInstanceState.getIntegerArrayList(SAVED_VECTORS)!!
            for (id in selectedViewsId) { findViewById<View>(id).visibility = View.INVISIBLE }
        } else setAllPortraitsVisible()
        setPortraits()
        initAd()
        showProgress(true)
        back_btn.setOnClickListener { finish() }
        setPositiveInstructionText()
        showProgress(false)
    }

    private fun initAd() {
        interstitialAd = InterstitialAd(this)
        interstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        interstitialAd.loadAd(AdRequest.Builder().build())
        interstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                showResult()
            }
        }
    }

    private fun showResult() {
        val result = ScaleResult(19, "Тест Сонди", Date().time, "", TestsTypes.SONDY, vectors)
        val intent = Intent(this, SondiResultActivity::class.java)
                .putExtra(SondiResultActivity.RESULT_DATA_PARAM, result)
        if (preferenceHelper.saveResultsEnabled()) dao.saveToHistory(result)
        startActivity(intent)
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntegerArrayList(SAVED_VIEWS, selectedViewsId)
        outState.putIntegerArrayList(SAVED_VECTORS, ArrayList(vectors))
        freezeInstanceState(outState)
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
        portrait_1.setImageDrawable(ContextCompat.getDrawable(this, resource1))
        portrait_2.setImageDrawable(ContextCompat.getDrawable(this, resource2))
        portrait_3.setImageDrawable(ContextCompat.getDrawable(this, resource3))
        portrait_4.setImageDrawable(ContextCompat.getDrawable(this, resource4))
        portrait_5.setImageDrawable(ContextCompat.getDrawable(this, resource5))
        portrait_6.setImageDrawable(ContextCompat.getDrawable(this, resource6))
        portrait_7.setImageDrawable(ContextCompat.getDrawable(this, resource7))
        portrait_8.setImageDrawable(ContextCompat.getDrawable(this, resource8))
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
                if (interstitialAd.isLoaded) interstitialAd.show()
                else {
                    showResult()
                }
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
            progress_bar.visibility = View.VISIBLE
            portraits_layout.visibility = View.INVISIBLE
        } else {
            progress_bar.visibility = View.INVISIBLE
            portraits_layout.visibility = View.VISIBLE
        }
    }

    private fun setAllPortraitsVisible() = portraitsViews.forEach { it.visibility = View.VISIBLE }


    private fun setPositiveInstructionText() {
        val span = SpannableBuilder("Выберите два наиболее приятных или симпатичных портрета из лежащих перед вами. Если не нравится ни один из них, то выберите два наименее неприятных.")
        span.setSpan(StyleSpan(android.graphics.Typeface.BOLD), 14, 38, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        instruction_text.text = span
    }

    private fun setNegativeInstructionText() {
        val span = SpannableBuilder("Выберите два самых неприятных, несимпатичных портрета из лежащих перед вами.")
        span.setSpan(StyleSpan(android.graphics.Typeface.BOLD), 14, 39, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        instruction_text.text = span
    }

}
