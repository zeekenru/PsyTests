package com.kovapps.kovalev.psytests.ui.result

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.enities.ThreeScalesResult


class ThreeScalesResultActivity : AppCompatActivity() {

    companion object {
        const val RESULT_DATA_PARAM = "result_data"
    }

    private lateinit var scale1Name: TextView
    private lateinit var scale2Name: TextView
    private lateinit var scale3Name: TextView
    private lateinit var scale1Result: TextView
    private lateinit var scale2Result: TextView
    private lateinit var scale3Result: TextView
    private lateinit var closeBtn: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maslach_result)
        scale1Name = findViewById(R.id.first_scale_name)
        scale2Name = findViewById(R.id.second_scale_name)
        scale3Name = findViewById(R.id.third_scale_name)
        scale1Result = findViewById(R.id.first_scale_result)
        scale2Result = findViewById(R.id.second_scale_result)
        scale3Result = findViewById(R.id.third_scale_result)
        closeBtn = findViewById(R.id.close_btn)
        closeBtn.setOnClickListener { finish() }
        val result: ThreeScalesResult = intent.getParcelableExtra(RESULT_DATA_PARAM)!!
        when (result.testId) {
            TestsTypes.EPI -> {
                scale1Name.text = getString(R.string.three_scales_result_1)
                scale2Name.text = getString(R.string.three_scales_result_2)
                scale3Name.text = getString(R.string.three_scales_result_3)
                when (result.firstScale) {
                    in (0..4) -> scale1Result.text = getString(R.string.three_scales_result_4)
                    in (5..8) -> scale1Result.text = getString(R.string.three_scales_result_5)
                    in (9..14) -> scale1Result.text = getString(R.string.three_scales_result_6)
                    in (15..18) -> scale1Result.text = getString(R.string.three_scales_result_7)
                    in (19..24) -> scale1Result.text = getString(R.string.three_scales_result_8)
                }
                when (result.secondScale) {
                    in (0..6) -> scale2Result.text = getString(R.string.three_scales_result_9)
                    in (7..13) -> scale2Result.text = getString(R.string.three_scales_result_10)
                    in (14..18) -> scale2Result.text = getString(R.string.three_scales_result_11)
                    in (19..24) -> scale2Result.text = getString(R.string.three_scales_result_12)
                }
                when (result.thirdScale) {
                    in (0..4) -> scale3Result.text = getString(R.string.three_scales_result_13)
                    in (5..9) -> scale3Result.text = getString(R.string.three_scales_result_14)
                }
            }
            TestsTypes.MASLACH -> {
                scale1Name.text = getString(R.string.three_scales_result_15)
                scale2Name.text = getString(R.string.three_scales_result_16)
                scale3Name.text = getString(R.string.three_scales_result_17)
                when (result.firstScale) {
                    in (0..15) -> scale1Result.text = getString(R.string.three_scales_result_9)
                    in (16..24) -> scale1Result.text = getString(R.string.three_scales_result_10)
                    in (25..55) -> scale1Result.text = getString(R.string.three_scales_result_11)
                }
                when (result.secondScale) {
                    in (0..5) -> scale2Result.text = getString(R.string.three_scales_result_9)
                    in (6..10) -> scale2Result.text = getString(R.string.three_scales_result_10)
                    in (11..30) -> scale2Result.text = getString(R.string.three_scales_result_11)
                }
                when (result.thirdScale) {
                    in (37..48) -> scale3Result.text = getString(R.string.three_scales_result_9)
                    in (31..36) -> scale3Result.text = getString(R.string.three_scales_result_10)
                    in (0..30) -> scale3Result.text = getString(R.string.three_scales_result_11)
                }
            }
        }
    }

}
