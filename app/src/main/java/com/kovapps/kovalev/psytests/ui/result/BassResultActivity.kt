package com.kovapps.kovalev.psytests.ui.result

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.enities.ScaleResult
import java.util.*

class BassResultActivity : AppCompatActivity() {


    companion object {
        const val RESULT_DATA_PARAM = "test"
    }

    private lateinit var result: ScaleResult
    private lateinit var closeBtn: ImageView
    private lateinit var scale1: TextView
    private lateinit var scale2: TextView
    private lateinit var scale3: TextView
    private lateinit var scale4: TextView
    private lateinit var scale5: TextView
    private lateinit var scale6: TextView
    private lateinit var scale7: TextView
    private lateinit var scale8: TextView
    private lateinit var scale9: TextView
    private lateinit var scale10: TextView

    private lateinit var scale1Value: TextView
    private lateinit var scale2Value: TextView
    private lateinit var scale3Value: TextView
    private lateinit var scale4Value: TextView
    private lateinit var scale5Value: TextView
    private lateinit var scale6Value: TextView
    private lateinit var scale7Value: TextView
    private lateinit var scale8Value: TextView
    private lateinit var scale9Value: TextView
    private lateinit var scale10Value: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bass_result)
        closeBtn = findViewById(R.id.close_btn)
        scale1 = findViewById(R.id.scale_1)
        scale2 = findViewById(R.id.scale_2)
        scale3 = findViewById(R.id.scale_3)
        scale4 = findViewById(R.id.scale_4)
        scale5 = findViewById(R.id.scale_5)
        scale6 = findViewById(R.id.scale_6)
        scale7 = findViewById(R.id.scale_7)
        scale8 = findViewById(R.id.scale_8)
        scale9 = findViewById(R.id.scale_9)
        scale10 = findViewById(R.id.scale_10)
        scale1Value = findViewById(R.id.scale_1_value)
        scale2Value = findViewById(R.id.scale_2_value)
        scale3Value = findViewById(R.id.scale_3_value)
        scale4Value = findViewById(R.id.scale_4_value)
        scale5Value = findViewById(R.id.scale_5_value)
        scale6Value = findViewById(R.id.scale_6_value)
        scale7Value = findViewById(R.id.scale_7_value)
        scale8Value = findViewById(R.id.scale_8_value)
        scale9Value = findViewById(R.id.scale_9_value)
        scale10Value = findViewById(R.id.scale_10_value)


        closeBtn.setOnClickListener { finish() }
        result = if (!intent.hasExtra(RESULT_DATA_PARAM)) {
            ScaleResult(
                testId = TestsTypes.BASS,
                testName = "Диагностика состояния агрессии",
                date = Date().time,
                tesType = TestsTypes.BASS,
                scalesValues = listOf(7, 1, 5, 4, 2, 9, 3, 7)
            )
        } else intent.getParcelableExtra(RESULT_DATA_PARAM)!!
        scale1Value.text = "${result.scalesValues[0]}/10"
        scale2Value.text = "${result.scalesValues[1]}/9"
        scale3Value.text = "${result.scalesValues[2]}/11"
        scale4Value.text = "${result.scalesValues[3]}/5"
        scale5Value.text = "${result.scalesValues[4]}/8"
        scale6Value.text = "${result.scalesValues[5]}/10"
        scale7Value.text = "${result.scalesValues[6]}/13"
        scale8Value.text = "${result.scalesValues[7]}/9"
        calculateIndex(result.scalesValues)
    }

    private fun calculateIndex(values: List<Int>) {
        val firstIndex = values[0] + values[2] + values[6]
        val secondIndex = values[4] + values[5]
        val text = when (firstIndex) {
            in 25..31 -> getString(R.string.high_level).apply {
                scale9.setTextColor(
                    ContextCompat
                        .getColor(this@BassResultActivity, android.R.color.holo_red_dark)
                )
            }
            in 17..24 -> getString(R.string.middle_level)
            in 0..16 -> getString(R.string.low_level)
            else -> throw IllegalArgumentException("unexpected index value")
        }
        scale9Value.text = text
        val secondText = when (secondIndex) {
            in 11..18 -> getString(R.string.high_level).apply {
                scale10Value.setTextColor(
                    ContextCompat
                        .getColor(this@BassResultActivity, android.R.color.holo_red_dark)
                )
            }
            in 4..10 -> getString(R.string.middle_level)
            in 0..3 -> getString(R.string.low_level)
            else -> throw IllegalArgumentException("unexpected index value")
        }
        scale10Value.text = secondText

    }

}
