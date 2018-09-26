package com.kovapps.kovalev.psytests.ui.result

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kovapps.kovalev.psytests.dialogs.InterpretationFragment
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.enities.ThreeScalesResult
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_epi_result.*


class ThreeScalesResultActivity : AppCompatActivity() {

    companion object {
        const val RESULT_DATA_PARAM = "result_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_epi_result)
        Logger.d(this.javaClass.simpleName)
        close_btn.setOnClickListener { finish() }
        val result : ThreeScalesResult = intent.getParcelableExtra(RESULT_DATA_PARAM)
        when (result.testType){
            TestsTypes.EPI ->{
                first_scale_name.text = "Экстраверсия"
                second_scale_name.text = "Нейротизм"
                third_scale_name.text = "Ложь"
                when (result.firstScale){
                    in (0..4) -> first_scale_result.text = "Глубокий интроверт"
                    in (5..8) -> first_scale_result.text = "Интроверт"
                    in (9..14) -> first_scale_result.text = "Амбивалентность, неопределённость"
                    in (15..18) -> first_scale_result.text = "Экстраверт"
                    in (19..24) -> first_scale_result.text = "Яркий экстраверт"
                }
                when (result.secondScale){
                    in (0..6) -> second_scale_result.text = "Низкий уровень"
                    in (7..13) -> second_scale_result.text = "Средний уровень"
                    in (14..18) ->second_scale_result.text = "Высокий уровень"
                    in (19..24) -> second_scale_result.text = "Очень высокий уровень"
                }
                when (result.thirdScale){
                    in (0..4) -> third_scale_result.text = "Норма"
                    in (5..9) -> third_scale_result.text = "Неискренность в ответах"
                }
            }
            TestsTypes.MASLACH ->{
                first_scale_name.text = "Эмоциаональное истощение"
                second_scale_name.text = "Деперсонализация"
                third_scale_name.text = "Редукция проффесионализма"
                when (result.firstScale){
                    in (0..15) -> first_scale_result.text = "Низкий уровень"
                    in (16..24) -> first_scale_result.text = "Средний уровень"
                    in (25..55) -> first_scale_result.text = "Высокий уровень"
                }
                when (result.secondScale){
                    in (0..5) -> second_scale_result.text = "Низкий уровень"
                    in (6..10) -> second_scale_result.text = "Средний уровень"
                    in (11..30) -> second_scale_result.text = "Высокий уровень"
                }
                when (result.thirdScale){
                    in (37..48) -> third_scale_result.text = "Низкий уровень"
                    in (31..36) -> third_scale_result.text = "Средний уровень"
                    in (0..30) -> third_scale_result.text = "Высокий уровень"
                }
            }
        }
        info_btn.setOnClickListener {
            InterpretationFragment.getInstance(result.interpretation).show(supportFragmentManager, "tag")
        }
        share_btn.setOnClickListener {
            val textMessage = when (result.testType){
                TestsTypes.EPI -> """Пройден личностный опросник Айзенка в приложение PsyTests:
                    |${first_scale_result.text}, ${second_scale_result.text.toString().toLowerCase()} нейротизма"""
                    .trimMargin()
                TestsTypes.MASLACH -> "Пройден личностный опросник выгорания Маслач в приложении PsyTests: " +
                        "${first_scale_result.text.toString().toLowerCase()} эмоцианального истощения, " +
                        "${second_scale_result.text.toString().toLowerCase()} деперсонализации, " +
                        "${third_scale_result.text.toString().toLowerCase()} редукции професионализма"
                else -> throw IllegalArgumentException("Unexpected test type")
            }
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type  = "text/plain"
                putExtra(Intent.EXTRA_TEXT, textMessage)
            }
            startActivity(intent)
        }
    }

}
