package com.kovapps.kovalev.psytests.ui.result

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kovapps.kovalev.psytests.dialogs.InterpretationFragment
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes.BECK_DEPRESSION
import com.kovapps.kovalev.psytests.TestsTypes.BECK_HOPELESSNESS
import com.kovapps.kovalev.psytests.TestsTypes.BRETMEN
import com.kovapps.kovalev.psytests.TestsTypes.EQ
import com.kovapps.kovalev.psytests.TestsTypes.KARPOV_REFLECTION
import com.kovapps.kovalev.psytests.TestsTypes.OUB
import com.kovapps.kovalev.psytests.TestsTypes.ZUNG_ANXIETY
import com.kovapps.kovalev.psytests.TestsTypes.ZUNG_DEPRESSION
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.OneScaleResult
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_result.*
import toothpick.Toothpick
import javax.inject.Inject

class OneScaleResultActivity : AppCompatActivity() {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    init {
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
    }

    companion object {
        const val RESULT_DATA_PARAM = "result_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        Logger.d(this.javaClass.simpleName)
        close_btn.setOnClickListener {
            finish()
        }
        val resultData: OneScaleResult = intent.getParcelableExtra(RESULT_DATA_PARAM)
        val summary = resultData.scale
        Logger.d(resultData)
        when (resultData.testType) {
            BECK_DEPRESSION -> {
                summary_text.text = "$summary из 63"
                when (summary) {
                    in (0..9) -> first_scale_result.text = "Отсутствие депрессивных симптомов"
                    in (10..15) -> first_scale_result.text = "Легкая депрессия (субдепрессия)"
                    in (16..19) -> first_scale_result.text = "Умеренная депрессия"
                    in (20..29) -> first_scale_result.text = "Выраженная депрессия (средней тяжести)"
                    in (30..63) -> first_scale_result.text = "Тяжелая депрессия"
                }
            }
            ZUNG_DEPRESSION -> {
                summary_text.text = "$summary из 80"
                when (summary) {
                    in (1..49) -> first_scale_result.text = "Нормальное состояние"
                    in (50..59) -> first_scale_result.text = "Легкая депрессия"
                    in (60..69) -> first_scale_result.text = "Умеренная депрессия"
                    in (70..80) -> first_scale_result.text = "Тяжелая депрессия"
                }
            }
            BECK_HOPELESSNESS -> {
                summary_text.text = "$summary из 80"
                when (summary) {
                    in (1..49) -> first_scale_result.text = "Нормальное состояние"
                    in (50..59) -> first_scale_result.text = "Негативное отношение к будущеему"
                    in (60..69) -> first_scale_result.text = "Умеренное ощущение безнадежности"
                    in (70..80) -> first_scale_result.text = "Сильное ощущение безнадежности"
                }
            }
            ZUNG_ANXIETY -> {
                summary_text.text = "$summary из 80"
                when (summary) {
                    in (20..40) -> first_scale_result.text = "Низкий уровень тревожности"
                    in (41..60) -> first_scale_result.text = "Средний уровень тревожности"
                    in (61..80) -> first_scale_result.text = "Высокий уровень тревожности"
                }
            }
            KARPOV_REFLECTION -> {

                summary_text.text = "$summary из 189"

                when (summary) {
                    in (0..113) -> first_scale_result.text = "Низкий уровень развития рефлексивности"
                    in (114..147) -> first_scale_result.text = "Средний уровень развития рефлексивности"
                    in (147..189) -> first_scale_result.text = "Высокий уровень развития рефлексивности"
                }
            }
            BRETMEN -> {
                summary_text.text = "$summary из 10"
                if (summary <= 4) {
                    first_scale_result.text = "Нервная орторексия отсутствует"
                } else {
                    first_scale_result.text = "Присутсвует вероятность нервной орторексии"
                }
            }
            OUB -> {
                summary_text.text = "$summary из 48"
                when (summary) {
                    in (0..16) -> first_scale_result.text = "Абсолютно неблагополучная семья"
                    in (17..22) -> first_scale_result.text = "Высокая степень неудовлетворенности"
                    in (23..26) -> first_scale_result.text = "Выраженная неудовлетворенность"
                    in (27..28) -> first_scale_result.text = "Переходный статус"
                    in (29..32) -> first_scale_result.text = "Удовлетворенность браком"
                    in (33..38) -> first_scale_result.text = "Благополучная семья"
                    in (39..48) -> first_scale_result.text = "Абсолютно благополучная семья"
                }
            }
            EQ -> {
                summary_text.text = "$summary из 80"
                when (summary){
                    in (0..17) -> first_scale_result.text = "Очень низкий уровень сопереживания"
                    in (17..28) -> first_scale_result.text = "Низкий уровень сопереживания"
                    in (29..51) -> first_scale_result.text = "Средний уровень сопереживания"
                    in (52..63) -> first_scale_result.text = "Высокий уровень сопереживания"
                    in (63..80) -> first_scale_result.text = "Очень высокий уровень сопереживания"
                }
            }
        }

        interpretation_button.setOnClickListener {
            InterpretationFragment.getInstance(resultData.interpretation).show(supportFragmentManager, "tag")
        }
        share_btn.setOnClickListener {
           var shareIntentText = when (resultData.testType){
               BECK_DEPRESSION -> "Пройдена шкала депрессии Бека в приложение PsyTests: "
               ZUNG_DEPRESSION -> "Пройдена шкала самооценки депрессии Цунга в приложение PsyTests: "
               BECK_HOPELESSNESS -> "Пройдена шкала безнадежности Бека в приложение PsyTests: "
               ZUNG_ANXIETY -> "Пройдена шкала самооценки тревоги Цунга в приложение PsyTests: "
               KARPOV_REFLECTION -> "Пройден опросник рефлексивности Карпова в приложение PsyTests: "
               BRETMEN -> "Пройден опросник нервной орторексии Бретмена в приложение PsyTests: "
               OUB -> "Пройден опросник удовлетворенности браком в приложение PsyTests: "
               EQ -> "Пройден опросник уровня сопереживания в приложение PsyTests: "
               else -> throw IllegalArgumentException("Unexpected test type")

            }
            shareIntentText += "${first_scale_result.text.toString().toLowerCase()}."
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type  = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareIntentText)
            }
            startActivity(shareIntent)

        }

    }

}
