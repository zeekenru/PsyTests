package com.kovapps.kovalev.psytests.ui.result

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes.BECK_DEPRESSION
import com.kovapps.kovalev.psytests.TestsTypes.BECK_HOPELESSNESS
import com.kovapps.kovalev.psytests.TestsTypes.BRETMEN
import com.kovapps.kovalev.psytests.TestsTypes.EQ
import com.kovapps.kovalev.psytests.TestsTypes.KARPOV_REFLECTION
import com.kovapps.kovalev.psytests.TestsTypes.OUB
import com.kovapps.kovalev.psytests.TestsTypes.TEST_14
import com.kovapps.kovalev.psytests.TestsTypes.TEST_15
import com.kovapps.kovalev.psytests.TestsTypes.TEST_16
import com.kovapps.kovalev.psytests.TestsTypes.TEST_17
import com.kovapps.kovalev.psytests.TestsTypes.TEST_21
import com.kovapps.kovalev.psytests.TestsTypes.ZUNG_ANXIETY
import com.kovapps.kovalev.psytests.TestsTypes.ZUNG_DEPRESSION
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.OneScaleResult
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import toothpick.Toothpick
import javax.inject.Inject

class OneScaleResultActivity : AppCompatActivity() {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private lateinit var closeBtn: ImageView
    private lateinit var summaryText: TextView
    private lateinit var scale1: TextView

    init {
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
    }

    companion object {
        const val RESULT_DATA_PARAM = "result_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        closeBtn = findViewById(R.id.close_btn)
        summaryText = findViewById(R.id.summary_text)
        scale1 = findViewById(R.id.first_scale_result)
        closeBtn.setOnClickListener { finish() }
        val resultData: OneScaleResult = intent.getParcelableExtra(RESULT_DATA_PARAM)!!
        val summary = resultData.scale
        when (resultData.testId) {
            BECK_DEPRESSION -> {
                summaryText.text = "$summary/63"
                when (summary) {
                    in (0..9) -> scale1.text = getString(R.string.one_scale_result_1)
                    in (10..15) -> scale1.text = getString(R.string.one_scale_result_2)
                    in (16..19) -> scale1.text = getString(R.string.one_scale_result_3)
                    in (20..29) -> scale1.text = getString(R.string.one_scale_result_4)
                    in (30..63) -> scale1.text =
                        getString(R.string.one_scale_result_5).apply { paintText() }
                }
            }
            ZUNG_DEPRESSION -> {
                summaryText.text = "$summary/80"
                when (summary) {
                    in (1..49) -> scale1.text = getString(R.string.one_scale_result_6)
                    in (50..59) -> scale1.text = getString(R.string.one_scale_result_7)
                    in (60..69) -> scale1.text = getString(R.string.one_scale_result_8)
                    in (70..80) -> scale1.text =
                        getString(R.string.one_scale_result_9).apply { paintText() }
                }
            }
            BECK_HOPELESSNESS -> {
                summaryText.text = "$summary/80"
                when (summary) {
                    in (1..49) -> scale1.text = getString(R.string.one_scale_result_10)
                    in (50..59) -> scale1.text = getString(R.string.one_scale_result_11)
                    in (60..69) -> scale1.text = getString(R.string.one_scale_result_12)
                    in (70..80) -> scale1.text =
                        getString(R.string.one_scale_result_13).apply { paintText() }
                }
            }
            ZUNG_ANXIETY -> {
                summaryText.text = "$summary/80"
                when (summary) {
                    in (20..40) -> scale1.text = getString(R.string.one_scale_result_14)
                    in (41..60) -> scale1.text = getString(R.string.one_scale_result_15)
                    in (61..80) -> scale1.text =
                        getString(R.string.one_scale_result_16).apply { paintText() }
                }
            }
            KARPOV_REFLECTION -> {

                summaryText.text = "$summary/189"

                when (summary) {
                    in (0..113) -> scale1.text = getString(R.string.one_scale_result_17)
                    in (114..147) -> scale1.text = getString(R.string.one_scale_result_18)
                    in (147..189) -> scale1.text = getString(R.string.one_scale_result_19)
                }
            }
            BRETMEN -> {
                summaryText.text = "$summary/10"
                if (summary <= 4) {
                    scale1.text = getString(R.string.one_scale_result_20)
                } else {
                    scale1.text = getString(R.string.one_scale_result_21)
                }
            }
            OUB -> {
                summaryText.text = "$summary/48"
                when (summary) {
                    in (0..16) -> scale1.text = getString(R.string.one_scale_result_22)
                    in (17..22) -> scale1.text = getString(R.string.one_scale_result_23)
                    in (23..26) -> scale1.text = getString(R.string.one_scale_result_24)
                    in (27..28) -> scale1.text = getString(R.string.one_scale_result_25)
                    in (29..32) -> scale1.text = getString(R.string.one_scale_result_26)
                    in (33..38) -> scale1.text = getString(R.string.one_scale_result_27)
                    in (39..48) -> scale1.text = getString(R.string.one_scale_result_28)
                }
            }
            EQ -> {
                summaryText.text = "$summary/80"
                when (summary) {
                    in (0..17) -> scale1.text = getString(R.string.one_scale_result_29)
                    in (17..28) -> scale1.text = getString(R.string.one_scale_result_30)
                    in (29..51) -> scale1.text = getString(R.string.one_scale_result_31)
                    in (52..63) -> scale1.text = getString(R.string.one_scale_result_32)
                    in (63..80) -> scale1.text = getString(R.string.one_scale_result_33)
                }
            }
            TEST_14 -> {
                summaryText.text = "$summary/19"
                when (summary) {
                    in (0..11) -> scale1.text = getString(R.string.one_scale_result_34)
                    in (12..15) -> scale1.text = getString(R.string.one_scale_result_35)
                    in (16..19) -> scale1.text = getString(R.string.one_scale_result_36)
                }
            }
            TEST_15 -> {
                summaryText.text = "$summary/19"
                when (summary) {
                    in (0..11) -> scale1.text = getString(R.string.one_scale_result_37)
                    in (12..15) -> scale1.text = getString(R.string.one_scale_result_38)
                    in (16..22) -> scale1.text = getString(R.string.one_scale_result_39)
                }
            }
            TEST_16 -> {
                summaryText.text = "$summary/50"
                val text = when (summary) {
                    in (0..5) -> getString(R.string.one_scale_result_40)
                    in (6..25) -> getString(R.string.one_scale_result_41)
                    in (26..40) -> getString(R.string.one_scale_result_42).apply { paintText() }
                    in (41..50) -> getString(R.string.one_scale_result_43).apply { paintText() }
                    else -> ""
                }
                scale1.text = text
            }
            TEST_17 -> {
                summaryText.text = "$summary/40"
                val text = when (summary) {
                    in (0..23) -> getString(R.string.one_scale_result_44)
                    in (24..40) -> getString(R.string.one_scale_result_45).apply { paintText() }
                    else -> ""
                }
                scale1.text = text
            }
            TEST_21 -> {
                scale1.text = "$summary/160"
                val text = when (summary) {
                    in (0..69) -> getString(R.string.one_scale_result_46)
                    in (70..119) -> getString(R.string.one_scale_result_47)
                    in (120..160) -> getString(R.string.one_scale_result_48).apply { paintText() }
                    else -> getString(R.string.one_scale_result_49)
                }
                scale1.text = text
            }


        }

    }


    private fun paintText() {
        scale1.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
    }

}
