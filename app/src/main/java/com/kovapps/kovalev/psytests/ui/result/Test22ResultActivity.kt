package com.kovapps.kovalev.psytests.ui.result

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.enities.ScaleResult


class Test22ResultActivity : AppCompatActivity() {

    companion object {
        const val RESULT_DATA_PARAM = "result"
    }

    private lateinit var result: ScaleResult

    private lateinit var closeBtn: View
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

    private lateinit var scaleResult1: TextView
    private lateinit var scaleResult2: TextView
    private lateinit var scaleResult3: TextView
    private lateinit var scaleResult4: TextView
    private lateinit var scaleResult5: TextView
    private lateinit var scaleResult6: TextView
    private lateinit var scaleResult7: TextView
    private lateinit var scaleResult8: TextView
    private lateinit var scaleResult9: TextView
    private lateinit var scaleResult10: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_22_result)
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
        scale9Value = findViewById(R.id.scale_9_value)
        scale10Value = findViewById(R.id.scale_10_value)

        scaleResult1 = findViewById(R.id.scale_1_result)
        scaleResult2 = findViewById(R.id.scale_2_result)
        scaleResult3 = findViewById(R.id.scale_3_result)
        scaleResult4 = findViewById(R.id.scale_4_result)
        scaleResult5 = findViewById(R.id.scale_5_result)
        scaleResult6 = findViewById(R.id.scale_6_result)
        scaleResult7 = findViewById(R.id.scale_7_result)
        scaleResult8 = findViewById(R.id.scale_8_result)
        scaleResult9 = findViewById(R.id.scale_9_result)
        scaleResult10 = findViewById(R.id.scale_10_result)


        result = intent.getParcelableExtra(RESULT_DATA_PARAM)!!
        scale1Value.text = "${result.scalesValues[0]}/32"
        scale2Value.text = "${result.scalesValues[1]}/32"
        scale3Value.text = "${result.scalesValues[2]}/32"
        scale4Value.text = "${result.scalesValues[3]}/32"
        scale5Value.text = "${result.scalesValues[4]}/32"
        scale6Value.text = "${result.scalesValues[5]}/32"
        scale7Value.text = "${result.scalesValues[6]}/32"
        scale8Value.text = "${result.scalesValues[7]}/32"
        scale9Value.text = "${result.scalesValues[8]}/32"
        scale10Value.text = "${result.scalesValues[9]}/32"
        scaleResult1.text = when (result.scalesValues[0]) {
            in (0..15) -> getString(R.string.test22_result_1)
            in (16..32) -> getString(R.string.test22_result_2)
            else -> getString(R.string.test22_result_3)

        }
        scaleResult3.text = when (result.scalesValues[1]) {
            in (0..17) -> getString(R.string.test22_result_4)
            in (18..32) -> getString(R.string.test22_result_5)
            else -> getString(R.string.test22_result_6)
        }
        scaleResult2.text = when (result.scalesValues[2]) {
            in (0..15) -> getString(R.string.test22_result_7)
            in (16..32) -> getString(R.string.test22_result_8)
            else -> getString(R.string.test22_result_9)
        }
        scaleResult4.text = when (result.scalesValues[3]) {
            in (0..13) -> getString(R.string.test22_result_10)
            in (14..32) -> getString(R.string.test22_result_11)
            else -> getString(R.string.test22_result_12)
        }
        scaleResult5.text = when (result.scalesValues[4]) {
            in (0..17) -> getString(R.string.test22_result_13)
            in (18..32) -> getString(R.string.test22_result_14)
            else -> getString(R.string.test22_result_15)
        }
        scaleResult6.text = when (result.scalesValues[5]) {
            in (0..10) -> getString(R.string.test22_result_16)
            in (11..32) -> getString(R.string.test22_result_17)
            else -> getString(R.string.test22_result_18)
        }
        scaleResult7.text = when (result.scalesValues[6]) {
            in (0..13) -> getString(R.string.test22_result_19)
            in (14..32) -> getString(R.string.test22_result_20)
            else -> getString(R.string.test22_result_21)
        }
        scaleResult8.text = when (result.scalesValues[7]) {
            in (0..15) -> getString(R.string.test22_result_22)
            in (16..32) -> getString(R.string.test22_result_23)
            else -> ""
        }
        scaleResult9.text = when (result.scalesValues[8]) {
            in (0..18) -> getString(R.string.test22_result_24)
            in (19..32) -> getString(R.string.test22_result_25)
            else -> getString(R.string.test22_result_26)
        }
        scaleResult10.text = when (result.scalesValues[9]) {
            in (0..12) -> getString(R.string.test22_result_27)
            in (13..32) -> getString(R.string.test22_result_28)
            else -> getString(R.string.test22_result_29)
        }
        closeBtn.setOnClickListener {
            finish()
        }

    }

}
