package com.kovapps.kovalev.psytests.ui.result

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kovapps.kovalev.psytests.R
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_maslach_result.*

class MaslachResultActivity : AppCompatActivity() {

    companion object {
        const val FIRST_SCALE = "first"
        const val SECOND_SCALE = "second"
        const val THIRST_SCALE = "thirst"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maslach_result)
        Logger.d(this.javaClass.simpleName)
        val firstScale = intent.getIntExtra(FIRST_SCALE, 1)
        val secondScale = intent.getIntExtra(SECOND_SCALE, 1)
        val thirstScale = intent.getIntExtra(THIRST_SCALE, 1)
        first_scale_summary.text = "$firstScale/54"
        second_scale_summary.text = "$secondScale/30"
        third_scale_summary.text = "$thirstScale/48"
        when (firstScale){
            in (0..15) -> first_scale_result.text = getString(R.string.low_level)
            in (16..24) -> first_scale_result.text = getString(R.string.middle_level)
            in (25..55) -> first_scale_result.text = getString(R.string.high_level)
        }
        when (secondScale){
            in (0..5) -> second_scale_result.text = getString(R.string.low_level)
            in (6..10) -> second_scale_result.text =  getString(R.string.middle_level)
            in (11..30) -> second_scale_result.text = getString(R.string.high_level)
        }
        when (thirstScale){
            in (37..48) -> third_scale_result.text = getString(R.string.low_level)
            in (31..36) -> third_scale_result.text =  getString(R.string.middle_level)
            in (0..30) -> third_scale_result.text = getString(R.string.high_level)
        }
        close_btn.setOnClickListener {
            finish()
        }
        share_btn.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type  = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Пройден личностный опросник выгорания Маслач в приложении PsyTests: " +
                        "${first_scale_result.text.toString().toLowerCase()} эмоцианального истощения, " +
                        "${second_scale_result.text.toString().toLowerCase()} деперсонализации, " +
                        "${third_scale_result.text.toString().toLowerCase()} редукции професионализма")
            }
            startActivity(intent)
        }
    }
}
