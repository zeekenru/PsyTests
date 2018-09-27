package com.kovapps.kovalev.psytests.ui.result

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.dialogs.InterpretationFragment
import com.kovapps.kovalev.psytests.enities.ScaleResult
import kotlinx.android.synthetic.main.activity_bass_result.*

class BassResultActivity : AppCompatActivity(), View.OnClickListener {


    companion object {
        const val RESULT_DATA_PARAM = "test"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bass_result)
        close_btn.setOnClickListener { finish() }
        val result: ScaleResult = intent.getParcelableExtra(RESULT_DATA_PARAM)
        scale_1_value.text = "${result.scalesValues[0]}/10"
        scale_2_value.text = "${result.scalesValues[1]}/9"
        scale_3_value.text = "${result.scalesValues[2]}/11"
        scale_4_value.text = "${result.scalesValues[3]}/5"
        scale_5_value.text = "${result.scalesValues[4]}/8"
        scale_6_value.text = "${result.scalesValues[5]}/10"
        scale_7_value.text = "${result.scalesValues[6]}/13"
        scale_8_value.text = "${result.scalesValues[7]}/9"
        scale_1.setOnClickListener(this)
        scale_2.setOnClickListener(this)
        scale_3.setOnClickListener(this)
        scale_4.setOnClickListener(this)
        scale_5.setOnClickListener(this)
        scale_6.setOnClickListener(this)
        scale_7.setOnClickListener(this)
        scale_8.setOnClickListener(this)
        scale_9.setOnClickListener(this)
        scale_10.setOnClickListener(this)
        calculateIndex(result.scalesValues)
    }

    private fun calculateIndex(values: List<Int>) {
        val firstIndex = values[0] + values[2] + values[6]
        val secondIndex = values[4] + values[5]
        val text = when (firstIndex ){
            in 25..31 -> "Высокий уровень"  .apply { scale_9_value.setTextColor(ContextCompat
                    .getColor(this@BassResultActivity, android.R.color.holo_red_dark)) }
            in 17..24 -> "Средний уровень"
            in 0..16 -> "Низкий уровень"
            else -> throw IllegalArgumentException("unexpected index value")
        }
        scale_9_value.text = text
        val secondText = when (secondIndex){
            in 11..18 -> "Высокий уровень"  .apply { scale_10_value.setTextColor(ContextCompat
                    .getColor(this@BassResultActivity, android.R.color.holo_red_dark)) }
            in 4..10 -> "Средний урвоень"
            in 0..3 -> "Низкий уровень"
            else -> throw IllegalArgumentException("unexpected index value")
        }
        scale_10_value.text = secondText

        share_btn.setOnClickListener {
            val shareIntentText = "Пройдена диагностика агрессии в приложении '${getString(R.string.app_name)}': " +
                    "${scale_9_value.text.toString().toLowerCase()} агрессивности, ${scale_10_value.text.toString().toLowerCase()} враждебности"
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareIntentText)
            }
            startActivity(shareIntent)
        }
    }

    override fun onClick(v: View) {
        val message : String =  when(v.id){
            R.id.scale_1 -> " Использование физической силы против другого лица"
            R.id.scale_2 -> "Действия, неявно направленные на другое лицо или на неодушевленные предметы"
            R.id.scale_3 -> "Повышенная нервность, возбудимость, готовность к проявлению негативных реакций на незначительные раздражители"
            R.id.scale_4 -> "Оппозиционная манера в поведении, проявляющаяся в диапазоне от пассивного неприятия до активного сопротивления и борьбы"
            R.id.scale_5 -> "Негативное чувство, в основе которого лежат переживания человека по поводу допущенной по отношению к нему несправедливости (действительной или вымышленной), непонимания со стороны окружающих, ущемления интересов, задетого чувства собственного достоинства"
            R.id.scale_6 -> "Сложный комплекс чувств, проявляющийся в широком диапазоне: от недоверия и осторожности по отношению к другим людям до убежденности в том, что они вредят"
            R.id.scale_7 -> "Выражение негативных чувств посредством словесных реакций (проклятия, угрозы, сарказм)"
            R.id.scale_8 -> "Негативно окрашенные переживания, связанные с допущенной ошибкой, причиненным вредом, с чувством невыполненного долга, часто приводящие к снижению самооценки"
            R.id.scale_9 -> "Норма для показателя индекса агрессивности — 21 ± 4 балла"
            R.id.scale_10 -> "Норма для показателя индекса враждебности — 7 ± 3 балла"
            else -> throw IllegalArgumentException("Unexpected id ")
        }
        InterpretationFragment.getInstance(message).show(supportFragmentManager, "tag")
    }
}
