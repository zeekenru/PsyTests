package com.kovapps.kovalev.psytests.ui.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.enities.LusherColor
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_luscher.*
import kotlinx.android.synthetic.main.luscher_pause_laoyut.*
import java.util.*

class LusherActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var views: List<View>
    private val mp = LusherColor.values().toMutableList().shuffled()
    private lateinit var firstAnswers: ArrayDeque<LusherColor>
    private lateinit var secondAnswers: ArrayDeque<LusherColor>
    private var isFirstState = true
    private var answersCount = 0
    private lateinit var lastSelectedColorView : View
    private lateinit var interstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_luscher)
        interstitialAd = InterstitialAd(this)
        interstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        interstitialAd.loadAd(AdRequest.Builder().build())
        if (ad_view != null && resources.getBoolean(R.bool.isTablet)){
            ad_view!!.loadAd(AdRequest.Builder().build())
        }
        views = listOf(lusher_color_1, lusher_color_2, lusher_color_3,
                lusher_color_4, lusher_color_5, lusher_color_6, lusher_color_7, lusher_color_8)
        firstAnswers = ArrayDeque(views.size)
        paintViews()
        views.forEachIndexed { index, view ->
            view.setOnClickListener(this)
        }
        back_btn.setOnClickListener { finish() }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.lusher_color_1 -> {
                lusher_color_1.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(mp[0]) else secondAnswers.add(mp[0])
                answersCount++
                lastSelectedColorView = lusher_color_1
            }
            R.id.lusher_color_2 -> {
                lusher_color_2.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(mp[1]) else secondAnswers.add(mp[1])
                answersCount++
                lastSelectedColorView = lusher_color_2
            }
            R.id.lusher_color_3 -> {
                lusher_color_3.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(mp[2]) else secondAnswers.add(mp[2])
                answersCount++
                lastSelectedColorView = lusher_color_3
            }
            R.id.lusher_color_4 -> {
                lusher_color_4.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(mp[3]) else secondAnswers.add(mp[3])
                answersCount++
                lastSelectedColorView = lusher_color_4
            }
            R.id.lusher_color_5 -> {
                lusher_color_5.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(mp[4]) else secondAnswers.add(mp[4])
                answersCount++
                lastSelectedColorView = lusher_color_5
            }
            R.id.lusher_color_6 -> {
                lusher_color_6.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(mp[5]) else secondAnswers.add(mp[5])
                answersCount++
                lastSelectedColorView = lusher_color_6
            }
            R.id.lusher_color_7 -> {
                lusher_color_7.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(mp[6]) else secondAnswers.add(mp[6])
                answersCount++
                lastSelectedColorView = lusher_color_7
            }
            R.id.lusher_color_8 -> {
                lusher_color_8.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(mp[7]) else secondAnswers.add(mp[7])
                answersCount++
                lastSelectedColorView = lusher_color_8
            }

        }
        if (answersCount == 8) {
            if (isFirstState) {
                if (interstitialAd.isLoaded){
                    Logger.d("Ad is loaded")
                    interstitialAd.show()
                    interstitialAd.adListener = object : AdListener(){
                        override fun onAdClosed() {
                            super.onAdClosed()
                            showPauseScreen()
                        }
                    }
                } else showPauseScreen()
            }
            else showResult()
        }
    }

    private fun showResult() {

        Logger.d("Answers: $firstAnswers ; $secondAnswers")
    }

    private fun paintViews(){
        views.forEachIndexed { index, view ->
            view.setBackgroundColor(mp[index].color)
            view.visibility = View.VISIBLE
        }
    }

    private fun showPauseScreen() {
        back_btn.visibility = View.INVISIBLE
        instruction_text.visibility = View.INVISIBLE
        colors_cards_layout.visibility = View.INVISIBLE
        luscher_pause_layout.visibility = View.VISIBLE
        continue_btn.setOnClickListener {
            showSecondColorList()
        }
    }

    private fun showSecondColorList() {
        back_btn.visibility = View.VISIBLE
        instruction_text.visibility = View.VISIBLE
        colors_cards_layout.visibility = View.VISIBLE
        luscher_pause_layout.visibility = View.GONE
        close_btn.setOnClickListener {finish()}
        isFirstState = false
        answersCount = 0
        secondAnswers = ArrayDeque(views.size)
        mp.toMutableList().shuffle()
        paintViews()
    }
}
