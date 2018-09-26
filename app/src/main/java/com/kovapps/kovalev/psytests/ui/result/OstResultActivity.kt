package com.kovapps.kovalev.psytests.ui.result

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.dialogs.InterpretationFragment
import com.kovapps.kovalev.psytests.enities.ScaleResult
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_ost_result.*

class OstResultActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val TEST_PARAM = "test"
    }

    private val highRange = 9..12

    private val mediumRange = 5..8

    private val lowRange = 0..4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ost_result)
        Logger.d(this.javaClass.simpleName)
        close_btn.setOnClickListener { finish() }
        val result: ScaleResult = intent.getParcelableExtra(TEST_PARAM)
        instruction_text.text = analyzeResult(result.scalesValues)
        scale_1.setOnClickListener(this)
        scale_2.setOnClickListener(this)
        scale_3.setOnClickListener(this)
        scale_4.setOnClickListener(this)
        scale_5.setOnClickListener(this)
        scale_6.setOnClickListener(this)
        scale_7.setOnClickListener(this)
        scale_8.setOnClickListener(this)
        scale_9.setOnClickListener(this)
        scale_1_value.text = "${result.scalesValues[0]}/12"
        scale_2_value.text = "${result.scalesValues[1]}/12"
        scale_3_value.text = "${result.scalesValues[2]}/12"
        scale_4_value.text = "${result.scalesValues[3]}/12"
        scale_5_value.text = "${result.scalesValues[4]}/12"
        scale_6_value.text = "${result.scalesValues[5]}/12"
        scale_7_value.text = "${result.scalesValues[6]}/12"
        scale_8_value.text = "${result.scalesValues[7]}/12"
        scale_9_value.text = "${result.scalesValues[8]}/9"

    }

    private fun analyzeResult(result: ArrayList<Int>): String {
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
            if (it !in highRange) isSanguine = false
        }
        if (isSanguine) return "Сангвиник"

        if (isCholeric) return "Холерик"

        result.forEach {
            if (it !in lowRange) isPhlegmatic = false
        }
        if (isPhlegmatic) return "Флегматик"

        if (isMelancholic) return "Меланхолик"
        return "Смешанный тип "
    }

    override fun onClick(v: View) {
       val message : String =  when(v.id){
            R.id.scale_1 ->  "Уровень потребности в освоении предметного мира, стремлении к умственному и физическому труду.\n" +
                    "\n" +
                    "Высокие значения - (9-12 баллов) по этой шкале означают высокую потребность в освоении предметного мира, жажду деятельности, стремление к напряженному умственному и физическому труду, легкость умственного пробуждения.\n" +
                    "Низкие значения - (3-4 балла) означают пассивность, низкий уровень тонуса и активации, нежелание умственного напряжения низкую вовлеченность в процесс деятельности."
            R.id.scale_2 -> "Уровень потребности в социальных контактах, о стремлении к лидерству.\n" +
                    "\n" +
                    "Высокие значения - коммуникативная эргичность, потребность в социальном контакте, жажда освоения социальных форм деятельности, стремление к лидерству, общительность, стремление к занятию высокого ранга, освоение мира через коммуникацию.\n" +
                    "Низкие значения - незначительная потребность в социальных контактах, уход от социально-активных форм поведения, замкнутость, социальная пассивность."
           R.id.scale_3 -> "Степень легкости или трудности переключения с одного предмета на другой, быстрота перехода с одних способов мышления на другие.\n" +
                   "\n" +
                   "Высокие значения - легкость переключения с одного вида деятельности на другой, быстрый переход с одних форм мышления на другие в процессе взаимодействия с предметной средой, стремление к разнообразию форм предметной деятельности.\n" +
                   "Низкие значения - склонность к монотонной работе, боязнь и избегание разнообразных форм поведения, вязкость, консервативные формы деятельности."
           R.id.scale_4 -> "Степень легкости или трудности переключения в процессе общения от одного человека к другому, склонности к разнообразию коммуникативных программ.\n" +
                   "\n" +
                   "Высокие значения - широкий набор коммуникативных программ, автоматическое включение в социальные связи, легкость вступления в социальные контакты, легкость переключения в процессе общения, наличие большого количества коммуникативных заготовок, коммуникативная импульсивность.\n" +
                   "Низкие значения - трудность в подборе форм социального взаимодействия, низкий уровень готовности к вступлению в социальные контакты, стремление к поддержанию монотонных контактов."
           R.id.scale_5 -> "Быстрота моторно-двигательных актов при выполнении предметной деятельности.\n" +
                   "\n" +
                   "Высокие значения - высокий темп поведения, высокая скорость выполнения операций при осуществлении предметной деятельности, моторно-двигательная быстрота, высокая психическая скорость при выполнении конкретных заданий."
           R.id.scale_6 -> "Скоростных характеристикы речедвигательных актов в процессе общения.\n" +
                   "\n" +
                   "Высокие значения - речедвигательная быстрота, быстрота говорения, высокие скорости и возможности речедвигательного аппарата.\n" +
                   "Низкие значения - слабо развита речедвигательная система, речевая медлительность, медленная вербализация."
           R.id.scale_7 -> "Эмоциональность, чувствительность, чувствительность к не удачам в работе.\n" +
                   "\n" +
                   "Высокие значения - высокая чувствительность к расхождению между задуманным и ожидаемым, планируемым и результатами реального действия, ощущения неуверенности, тревоги, неполноценности, высокое беспокойство по поводу работы, чувствительность к неудачам, к несовпадению между задуманным, ожидаемым, планируемым и результатами реального действия.\n" +
                   "Низкие значения - незначительное эмоциональное реагирование при неудачах, нечувствительность к неуспеху дела, спокойствие, уверенность в себе"
           R.id.scale_8 -> " Эмоциональная чувствительность в коммуникативной сфере.\n" +
                   "\n" +
                   "Высокие значения - высокая эмоциональность в коммуникативной сфере, высокая чувствительность к неудачам в общении.\n" +
                   "Низкие значения - низкая эмоциональность в коммуникативной сфере, нечувствительность к оценкам товарищей, отсутствие чувствительности к неудачам в общении, уверенность в себе и ситуациях общения."
           R.id.scale_9 -> "Шкала социальной желательности ответов -  откровенность и искренность высказываний.\n" +
                   "\n" +
                   "Высокие значения - неадекватная оценка своего поведения, желание выглядеть лучше, чем есть на самом деле.\n" +
                   "Низкие значения - адекватное восприятие своего поведения."
           else -> ""
        }
        InterpretationFragment.getInstance(message).show(supportFragmentManager, "tag")
    }
}
