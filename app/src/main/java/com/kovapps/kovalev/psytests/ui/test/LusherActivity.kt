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
import com.tinsuke.icekick.extension.freezeInstanceState
import com.tinsuke.icekick.extension.serialState
import com.tinsuke.icekick.extension.unfreezeInstanceState
import kotlinx.android.synthetic.main.activity_luscher.*
import kotlinx.android.synthetic.main.luscher_pause_laoyut.*
import java.util.*

class LusherActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val SAVED_VIEWS = "views"
        private const val FIRST_ANSWERS = "first_answers"
        private const val SECOND_ANSWERS = "second_answers"
        private const val COLORS = "colors"
        private const val CARDS_COUNT = 8
    }

    private lateinit var views: List<View>
    private val colors = LusherColor.values().toMutableList()
    private var firstAnswers = ArrayList<LusherColor>(CARDS_COUNT)
    private var secondAnswers =  ArrayList<LusherColor>(CARDS_COUNT)
    private var selectedViewsId = ArrayList<Int>(CARDS_COUNT)
    private var isFirstState : Boolean by serialState(true)
    private var answersCount : Int by serialState(0)
    private lateinit var lastSelectedColorView : View
    private lateinit var interstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_luscher)
        views = listOf(lusher_color_1, lusher_color_2, lusher_color_3,
                lusher_color_4, lusher_color_5, lusher_color_6, lusher_color_7, lusher_color_8)
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            unfreezeInstanceState(savedInstanceState)
            Logger.d("restore instance state")
            firstAnswers = savedInstanceState.getParcelableArrayList(FIRST_ANSWERS)!!
            secondAnswers = savedInstanceState.getParcelableArrayList(SECOND_ANSWERS)!!
            selectedViewsId = savedInstanceState.getIntegerArrayList(SAVED_VIEWS)!!
            colors.addAll(savedInstanceState.getParcelableArrayList(COLORS)!!)
            paintViews().apply { Logger.d("painting view") }
            for (id in selectedViewsId){
                findViewById<View>(id).visibility = View.INVISIBLE.apply { Logger.d("set invisible to : $id") }
                //views.forEachIndexed { index, view -> view.setBackgroundColor(colors[index].color).apply {  Logger.d("setting background") } }
            }
        } else {
            paintViews()
        }
        initAd()
        views.forEachIndexed { index, view ->
            view.setOnClickListener(this)
        }
        back_btn.setOnClickListener { finish() }
    }

    private fun initAd() {
        interstitialAd = InterstitialAd(this)
        interstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        interstitialAd.loadAd(AdRequest.Builder().build())
        if (ad_view != null && resources.getBoolean(R.bool.isTablet)){
            ad_view!!.loadAd(AdRequest.Builder().build())
        }
    }

    private fun randomizeColors() {
        colors.shuffle()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Logger.d("save instance state")
        outState.putIntegerArrayList(SAVED_VIEWS, selectedViewsId)
        outState.putParcelableArrayList(FIRST_ANSWERS, firstAnswers)
        outState.putParcelableArrayList(SECOND_ANSWERS, secondAnswers)
        outState.putParcelableArrayList(COLORS, ArrayList(colors))
        freezeInstanceState(outState)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.lusher_color_1 -> {
                lusher_color_1.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[0]) else secondAnswers.add(colors[0])
                answersCount++
                lastSelectedColorView = lusher_color_1
            }
            R.id.lusher_color_2 -> {
                lusher_color_2.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[1]) else secondAnswers.add(colors[1])
                answersCount++
                lastSelectedColorView = lusher_color_2
            }
            R.id.lusher_color_3 -> {
                lusher_color_3.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[2]) else secondAnswers.add(colors[2])
                answersCount++
                lastSelectedColorView = lusher_color_3
            }
            R.id.lusher_color_4 -> {
                lusher_color_4.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[3]) else secondAnswers.add(colors[3])
                answersCount++
                lastSelectedColorView = lusher_color_4
            }
            R.id.lusher_color_5 -> {
                lusher_color_5.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[4]) else secondAnswers.add(colors[4])
                answersCount++
                lastSelectedColorView = lusher_color_5
            }
            R.id.lusher_color_6 -> {
                lusher_color_6.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[5]) else secondAnswers.add(colors[5])
                answersCount++
                lastSelectedColorView = lusher_color_6
            }
            R.id.lusher_color_7 -> {
                lusher_color_7.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[6]) else secondAnswers.add(colors[6])
                answersCount++
                lastSelectedColorView = lusher_color_7
            }
            R.id.lusher_color_8 -> {
                lusher_color_8.visibility = View.INVISIBLE
                if (isFirstState) firstAnswers.add(colors[7]) else secondAnswers.add(colors[7])
                answersCount++
                lastSelectedColorView = lusher_color_8
            }
        }
        selectedViewsId.add(v.id)
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
            view.setBackgroundColor(colors[index].color)
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
        selectedViewsId.clear()
        back_btn.visibility = View.VISIBLE
        instruction_text.visibility = View.VISIBLE
        colors_cards_layout.visibility = View.VISIBLE
        luscher_pause_layout.visibility = View.GONE
        close_btn.setOnClickListener {finish()}
        isFirstState = false
        answersCount = 0
        colors.shuffle()
        paintViews()
    }
}
