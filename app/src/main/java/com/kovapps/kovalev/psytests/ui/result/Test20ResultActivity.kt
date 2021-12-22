package com.kovapps.kovalev.psytests.ui.result

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.enities.ScaleResult

class Test20ResultActivity : AppCompatActivity() {

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

    private lateinit var scale1Value: TextView
    private lateinit var scale2Value: TextView
    private lateinit var scale3Value: TextView
    private lateinit var scale4Value: TextView
    private lateinit var scale5Value: TextView
    private lateinit var scale6Value: TextView
    private lateinit var scale7Value: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_20_result)
        closeBtn = findViewById(R.id.close_btn)
        scale1 = findViewById(R.id.scale_1)
        scale2 = findViewById(R.id.scale_2)
        scale3 = findViewById(R.id.scale_3)
        scale4 = findViewById(R.id.scale_4)
        scale5 = findViewById(R.id.scale_5)
        scale6 = findViewById(R.id.scale_6)
        scale7 = findViewById(R.id.scale_7)


        scale1Value = findViewById(R.id.scale_1_value)
        scale2Value = findViewById(R.id.scale_2_value)
        scale3Value = findViewById(R.id.scale_3_value)
        scale4Value = findViewById(R.id.scale_4_value)
        scale5Value = findViewById(R.id.scale_5_value)
        scale6Value = findViewById(R.id.scale_6_value)
        scale7Value = findViewById(R.id.scale_7_value)


        result = intent.getParcelableExtra(RESULT_DATA_PARAM)!!
        val scale1res = when (result.scalesValues[0]) {
            in (-20..3) -> getString(R.string.test20_result_1)
            in (4..26) -> getString(R.string.test20_result_2)
            in (27..50) -> getString(R.string.test20_result_3)
            in (51..73) -> getString(R.string.test20_result_4)
            in (74..150) -> getString(R.string.test20_result_5)
            else -> getString(R.string.test20_result_3)
        }
        scale1Value.text = scale1res.replaceFirstChar { it.uppercase() }
        val scale2res = when (result.scalesValues[1]) {
            in (0..27) -> getString(R.string.test20_result_6)
            in (28..48) -> getString(R.string.test20_result_7)
            in (49..70) -> getString(R.string.test20_result_8)
            in (71..91) -> getString(R.string.test20_result_9)
            in (92..112) -> getString(R.string.test20_result_10)
            else -> getString(R.string.test20_result_8)
        }
        scale2Value.text = scale2res.replaceFirstChar { it.uppercase() }

        val scale3res = when (result.scalesValues[2]) {
            in (0..4) -> getString(R.string.test20_result_11)
            in (5..17) -> getString(R.string.test20_result_12)
            in (18..31) -> getString(R.string.test20_result_13)
            in (32..44) -> getString(R.string.test20_result_14)
            in (45..60) -> getString(R.string.test20_result_15)
            else -> getString(R.string.test20_result_13)
        }
        scale3Value.text = scale3res.replaceFirstChar { it.uppercase() }


        val scale4res = when (result.scalesValues[3]) {
            in (0..12) -> getString(R.string.test20_result_16)
            in (13..25) -> getString(R.string.test20_result_17)
            in (26..39) -> getString(R.string.test20_result_18)
            in (40..52) -> getString(R.string.test20_result_19)
            in (53..70) -> getString(R.string.test20_result_20)
            else -> getString(R.string.test20_result_18)
        }
        scale4Value.text = scale4res.replaceFirstChar { it.uppercase() }


        val scale5res = when (result.scalesValues[4]) {
            in (0..24) -> getString(R.string.test20_result_21)
            in (25..35) -> getString(R.string.test20_result_22)
            in (36..47) -> getString(R.string.test20_result_23)
            in (48..58) -> getString(R.string.test20_result_24)
            in (59..100) -> getString(R.string.test20_result_25)
            else -> getString(R.string.test20_result_23)
        }
        scale5Value.text = scale5res.replaceFirstChar { it.uppercase() }


        val scale6res = when (result.scalesValues[5]) {
            in (0..10) -> getString(R.string.test20_result_26)
            in (11..20) -> getString(R.string.test20_result_27)
            in (21..31) -> getString(R.string.test20_result_28)
            in (32..42) -> getString(R.string.test20_result_29)
            in (43..100) -> getString(R.string.test20_result_30)
            else -> getString(R.string.test20_result_28)
        }
        scale6Value.text = scale6res.replaceFirstChar { it.uppercase() }

        val scale7res = when (result.scalesValues[6]) {
            in (0..18) -> getString(R.string.test20_result_31)
            in (19..31) -> getString(R.string.test20_result_32)
            in (32..45) -> getString(R.string.test20_result_33)
            in (46..55) -> getString(R.string.test20_result_34)
            in (56..100) -> getString(R.string.test20_result_35)
            else -> getString(R.string.test20_result_33)
        }
        scale7Value.text = scale7res.replaceFirstChar { it.uppercase() }

        closeBtn.setOnClickListener {
            finish()
        }
    }

}
