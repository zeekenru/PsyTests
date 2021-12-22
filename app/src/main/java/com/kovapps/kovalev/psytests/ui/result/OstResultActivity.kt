package com.kovapps.kovalev.psytests.ui.result

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.enities.ScaleResult

class OstResultActivity : AppCompatActivity() {

    companion object {
        const val RESULT_DATA_PARAM = "test"
    }

    private val highRange = 9..12

    private val mediumRange = 5..8

    private val lowRange = 0..4

    private lateinit var closeBtn: ImageView

    private lateinit var temperamentValue: TextView

    private lateinit var scale1: TextView
    private lateinit var scale2: TextView
    private lateinit var scale3: TextView
    private lateinit var scale4: TextView
    private lateinit var scale5: TextView
    private lateinit var scale6: TextView
    private lateinit var scale7: TextView
    private lateinit var scale8: TextView
    private lateinit var scale9: TextView

    private lateinit var scale1Value: TextView
    private lateinit var scale2Value: TextView
    private lateinit var scale3Value: TextView
    private lateinit var scale4Value: TextView
    private lateinit var scale5Value: TextView
    private lateinit var scale6Value: TextView
    private lateinit var scale7Value: TextView
    private lateinit var scale8Value: TextView
    private lateinit var scale9Value: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ost_result)
        closeBtn = findViewById(R.id.close_btn)
        temperamentValue = findViewById(R.id.temperament_value)
        scale1 = findViewById(R.id.scale_1)
        scale2 = findViewById(R.id.scale_2)
        scale3 = findViewById(R.id.scale_3)
        scale4 = findViewById(R.id.scale_4)
        scale5 = findViewById(R.id.scale_5)
        scale6 = findViewById(R.id.scale_6)
        scale7 = findViewById(R.id.scale_7)
        scale8 = findViewById(R.id.scale_8)
        scale9 = findViewById(R.id.scale_9)

        scale1Value = findViewById(R.id.scale_1_value)
        scale2Value = findViewById(R.id.scale_2_value)
        scale3Value = findViewById(R.id.scale_3_value)
        scale4Value = findViewById(R.id.scale_4_value)
        scale5Value = findViewById(R.id.scale_5_value)
        scale6Value = findViewById(R.id.scale_6_value)
        scale7Value = findViewById(R.id.scale_7_value)
        scale8Value = findViewById(R.id.scale_8_value)
        scale9Value = findViewById(R.id.scale_9_value)

        closeBtn.setOnClickListener { finish() }
        val result: ScaleResult = intent.getParcelableExtra(RESULT_DATA_PARAM)!!
        temperamentValue.text = analyzeResult(result.scalesValues)
        val scale1ResultText : String = when(result.scalesValues[0]){
            in lowRange -> getString(R.string.ost_result_7)
            in mediumRange -> getString(R.string.ost_result_6)
            in highRange -> getString(R.string.ost_result_8)
            else -> getString(R.string.ost_result_6)
        }
        val scale2ResultText : String = when(result.scalesValues[1]){
            in lowRange -> getString(R.string.ost_result_9)
            in mediumRange -> getString(R.string.ost_result_6)
            in highRange -> getString(R.string.ost_result_10)
            else -> getString(R.string.ost_result_6)
        }
        val scale3ResultText : String = when(result.scalesValues[2]){
            in lowRange -> getString(R.string.ost_result_11)
            in mediumRange -> getString(R.string.ost_result_6)
            in highRange -> getString(R.string.ost_result_12)
            else -> getString(R.string.ost_result_6)
        }
        val scale4ResultText : String = when(result.scalesValues[3]){
            in lowRange -> getString(R.string.ost_result_13)
            in mediumRange -> getString(R.string.ost_result_6)
            in highRange -> getString(R.string.ost_result_14)
            else -> getString(R.string.ost_result_6)
        }
        val scale5ResultText : String = when(result.scalesValues[4]){
            in lowRange -> getString(R.string.ost_result_15)
            in mediumRange -> getString(R.string.ost_result_6)
            in highRange -> getString(R.string.ost_result_16)
            else -> getString(R.string.ost_result_6)
        }
        val scale6ResultText : String = when(result.scalesValues[5]){
            in lowRange -> getString(R.string.ost_result_17)
            in mediumRange -> getString(R.string.ost_result_6)
            in highRange -> getString(R.string.ost_result_18)
            else -> getString(R.string.ost_result_6)
        }
        val scale7ResultText : String = when(result.scalesValues[6]){
            in lowRange -> getString(R.string.ost_result_19)
            in mediumRange -> getString(R.string.ost_result_6)
            in highRange -> getString(R.string.ost_result_20)
            else -> getString(R.string.ost_result_6)
        }
        val scale8ResultText : String = when(result.scalesValues[7]){
            in lowRange -> getString(R.string.ost_result_21)
            in mediumRange -> getString(R.string.ost_result_6)
            in highRange -> getString(R.string.ost_result_22)
            else -> getString(R.string.ost_result_6)
        }
        val scale9ResultText : String = when(result.scalesValues[8]){
            in lowRange -> getString(R.string.ost_result_23)
            in mediumRange -> getString(R.string.ost_result_6)
            in highRange -> getString(R.string.ost_result_24)
            else -> getString(R.string.ost_result_6)
        }

        scale1Value.text = scale1ResultText.replaceFirstChar { it.uppercase() }
        scale2Value.text = scale2ResultText.replaceFirstChar { it.uppercase() }
        scale3Value.text = scale3ResultText.replaceFirstChar { it.uppercase() }
        scale4Value.text = scale4ResultText.replaceFirstChar { it.uppercase() }
        scale5Value.text = scale5ResultText.replaceFirstChar { it.uppercase() }
        scale6Value.text = scale6ResultText.replaceFirstChar { it.uppercase() }
        scale7Value.text = scale7ResultText.replaceFirstChar { it.uppercase() }
        scale8Value.text = scale8ResultText.replaceFirstChar { it.uppercase() }
        scale9Value.text = scale9ResultText.replaceFirstChar { it.uppercase() }



    }

    private fun analyzeResult(result: List<Int>): String {
        var isSanguine = true

        val isCholeric: Boolean = (result[0] in highRange
                && result[1] in highRange
                && result[6] in highRange
                && ((result[2] in highRange || result[2] in mediumRange)
                || (result[3] in highRange || result[3] in mediumRange)))
        var isPhlegmatic = true

        val isMelancholic: Boolean = (result[0] in lowRange
                && result[1] in lowRange
                && result[2] in lowRange
                && result[3] in lowRange
                && result[4] in lowRange
                && result[5] in lowRange
                && ((result[6] in highRange || result[6] in mediumRange)
                || (result[7] in highRange || result[7] in mediumRange)))

        result.forEach {
            if (it !in mediumRange) isSanguine = false
        }
        if (isSanguine) return getString(R.string.ost_result_1)

        if (isCholeric) return getString(R.string.ost_result_2)

        result.forEach {
            if (it !in lowRange) isPhlegmatic = false
        }
        if (isPhlegmatic) return getString(R.string.ost_result_3)

        if (isMelancholic) return getString(R.string.ost_result_4)
        return getString(R.string.ost_result_5)
    }


}
