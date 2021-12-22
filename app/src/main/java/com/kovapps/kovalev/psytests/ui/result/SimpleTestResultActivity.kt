package com.kovapps.kovalev.psytests.ui.result

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.SimpleTestResult
import toothpick.Toothpick

class SimpleTestResultActivity : AppCompatActivity() {

    init {
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
    }

    private lateinit var resultText: TextView
    private lateinit var pointsText: TextView
    private lateinit var backBtn: View

    companion object {
        const val RESULT_DATA_PARAM = "result_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_test_result)
        resultText = findViewById(R.id.result_text)
        pointsText = findViewById(R.id.points_text)
        backBtn = findViewById(R.id.close_btn)
        val resultData: SimpleTestResult = intent.getParcelableExtra(RESULT_DATA_PARAM)!!
        pointsText.text = "${resultData.points}/${resultData.results.last().max}"
        resultData.results.forEach { it ->
            if (resultData.points in it.min..it.max) {
                resultText.text = it.text.replaceFirstChar { it.uppercase() }
                if (it.text.length > 50) {
                    resultText.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
                    val params = resultText.layoutParams as ConstraintLayout.LayoutParams
                    params.marginEnd = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        8F,
                        this.resources.displayMetrics
                    ).toInt()
                    params.marginStart = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        24F,
                        this.resources.displayMetrics
                    ).toInt()
                    resultText.layoutParams = params
                }
            }
        }
        backBtn.setOnClickListener {
            finish()
        }
    }
}