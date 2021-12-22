package com.kovapps.kovalev.psytests.ui.result

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.enities.ScaleResult
import java.util.*

class SondiResultActivity : AppCompatActivity() {

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

    private lateinit var scale1Value: TextView
    private lateinit var scale2Value: TextView
    private lateinit var scale3Value: TextView
    private lateinit var scale4Value: TextView
    private lateinit var scale5Value: TextView
    private lateinit var scale6Value: TextView
    private lateinit var scale7Value: TextView
    private lateinit var scale8Value: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sondy_result)
        closeBtn = findViewById(R.id.sondi_close_btn)
        scale1 = findViewById(R.id.scale_1)
        scale2 = findViewById(R.id.scale_2)
        scale3 = findViewById(R.id.scale_3)
        scale4 = findViewById(R.id.scale_4)
        scale5 = findViewById(R.id.scale_5)
        scale6 = findViewById(R.id.scale_6)
        scale7 = findViewById(R.id.scale_7)
        scale8 = findViewById(R.id.scale_8)
        scale1Value = findViewById(R.id.scale_1_value)
        scale2Value = findViewById(R.id.scale_2_value)
        scale3Value = findViewById(R.id.scale_3_value)
        scale4Value = findViewById(R.id.scale_4_value)
        scale5Value = findViewById(R.id.scale_5_value)
        scale6Value = findViewById(R.id.scale_6_value)
        scale7Value = findViewById(R.id.scale_7_value)
        scale8Value = findViewById(R.id.scale_8_value)
        closeBtn.setOnClickListener {
            finish()
        }
        result = if (!intent.hasExtra(RESULT_DATA_PARAM)) {
            ScaleResult(
                19, "Sondi test", Date().time,
                TestsTypes.SONDY, listOf(0, 1, 2, 0, 3, 2, 3, 4)
            )
        } else intent.getParcelableExtra(RESULT_DATA_PARAM)!!

        val vectors = result.scalesValues
        val vectorH: String = when {
            vectors[0] < 2 -> getString(R.string.szondi_result_1)
            vectors[0] in 2 until 4 -> getString(R.string.szondi_result_2)
            vectors[0] > 3 -> getString(R.string.szondi_result_3)
            else -> ""
        }

        val vectorS: String = when {
            vectors[1] < 2 -> getString(R.string.szondi_result_4)
            vectors[1] >= 2 -> getString(R.string.szondi_result_5)
            else -> ""
        }
        val vectorE: String = when {
            vectors[2] < 2 -> getString(R.string.szondi_result_6)
            vectors[2] >= 2 -> getString(R.string.szondi_result_7)
            else -> ""
        }
        val vectorHy: String = when {
            vectors[3] < 2 -> getString(R.string.szondi_result_8)
            vectors[3] >= 2 -> getString(R.string.szondi_result_9)
            else -> ""
        }
        val vectorK: String = when {
            vectors[4] < 2 -> getString(R.string.szondi_result_10)
            vectors[4] >= 2 -> getString(R.string.szondi_result_11)
            else -> ""
        }
        val vectorP: String = when {
            vectors[5] < 2 -> getString(R.string.szondi_result_12)
            vectors[5] >= 2 -> getString(R.string.szondi_result_13)
            else -> ""
        }
        val vectorD: String = when {
            vectors[6] < 2 -> getString(R.string.szondi_result_14)
            vectors[6] >= 2 -> getString(R.string.szondi_result_15)
            else -> ""
        }
        val vectorM: String = when {
            vectors[7] < 2 -> getString(R.string.szondi_result_16)
            vectors[7] >= 2 -> getString(R.string.szondi_result_17)
            else -> ""
        }
        scale1Value.text = vectorH.replaceFirstChar { it.uppercase() }
        scale2Value.text = vectorS.replaceFirstChar { it.uppercase() }
        scale3Value.text = vectorE.replaceFirstChar { it.uppercase() }
        scale4Value.text = vectorHy.replaceFirstChar { it.uppercase() }
        scale5Value.text = vectorK.replaceFirstChar { it.uppercase() }
        scale6Value.text = vectorP.replaceFirstChar { it.uppercase() }
        scale7Value.text = vectorD.replaceFirstChar { it.uppercase() }
        scale8Value.text = vectorM.replaceFirstChar { it.uppercase() }
    }

}
