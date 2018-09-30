package com.kovapps.kovalev.psytests.ui.result

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.enities.ScaleResult
import kotlinx.android.synthetic.main.activity_five_scales.*

class FiveScalesResultActivity : AppCompatActivity() {

    companion object {
        const val RESULT_DATA_PARAM = "test"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_five_scales)
        val result : ScaleResult = intent.getParcelableExtra(RESULT_DATA_PARAM)
        close_btn.setOnClickListener { finish() }
        scale_1_value.text = "${result.scalesValues[0]} из 45"
        scale_2_value.text = "${result.scalesValues[1]} из 45"
        scale_3_value.text = "${result.scalesValues[2]} из 45"
        scale_4_value.text = "${result.scalesValues[3]} из 40"
        scale_5_value.text = "${result.scalesValues[4]} из 25"
        var sum = 0
        result.scalesValues.forEach { sum += it }
        val text = when (sum) {
            in (0..110) -> "Низкий балл"
            in (111..128) -> "Ниже среднего"
            in (129..146) -> "Средние значения"
            in (147..164) -> "Выше среднего"
            in (165..200) -> "Высокие значения"
            else -> throw  IllegalArgumentException("Unexpected summary")
        }
        scale_6_value.text = "$sum из 200"
        scale_6_summary.text = text
        info_btn.setOnClickListener {
            val intent = Intent(this, TestDescriptionActivity::class.java)
                    .putExtra(TestDescriptionActivity.TEST_ID_PARAM, result.id)
            startActivity(intent)
        }
    }
}
