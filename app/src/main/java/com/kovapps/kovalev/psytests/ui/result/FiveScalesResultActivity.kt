package com.kovapps.kovalev.psytests.ui.result

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.enities.ScaleResult


class FiveScalesResultActivity : AppCompatActivity() {

    companion object {
        const val RESULT_DATA_PARAM = "test"
    }

    private lateinit var scale1: TextView
    private lateinit var scale2: TextView
    private lateinit var scale3: TextView
    private lateinit var scale4: TextView
    private lateinit var scale5: TextView
    private lateinit var scale6: TextView
    private lateinit var scale6summary: TextView
    private lateinit var closeBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_five_scales)
        closeBtn = findViewById(R.id.close_btn)
        scale1 = findViewById(R.id.scale_1_value)
        scale2 = findViewById(R.id.scale_2_value)
        scale3 = findViewById(R.id.scale_3_value)
        scale4 = findViewById(R.id.scale_4_value)
        scale5 = findViewById(R.id.scale_5_value)
        scale6 = findViewById(R.id.scale_6_value)
        scale6summary = findViewById(R.id.scale_6_summary)
        val result: ScaleResult = intent.getParcelableExtra(RESULT_DATA_PARAM)!!
        closeBtn.setOnClickListener { finish() }
        scale1.text = "${result.scalesValues[0]} / 45"
        scale2.text = "${result.scalesValues[1]} / 45"
        scale3.text = "${result.scalesValues[2]} / 45"
        scale4.text = "${result.scalesValues[3]} / 40"
        scale5.text = "${result.scalesValues[4]} / 25"
        var sum = 0
        result.scalesValues.forEach { sum += it }
        val text = when (sum) {
            in (0..110) -> getString(R.string.five_scales_low_score)
            in (111..128) -> getString(R.string.five_scales_below_average)
            in (129..146) -> getString(R.string.five_scales_average_rates)
            in (147..164) -> getString(R.string.five_scale_values_above_average)
            in (165..200) -> getString(R.string.five_scales_high_index)
            else -> throw  IllegalArgumentException("Unexpected summary")
        }
        scale6.text = "$sum / 200"
        scale6summary.text = text.replaceFirstChar { it.uppercase() }
    }
}
