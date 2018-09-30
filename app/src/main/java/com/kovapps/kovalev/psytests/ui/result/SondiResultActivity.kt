package com.kovapps.kovalev.psytests.ui.result

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.enities.ScaleResult
import kotlinx.android.synthetic.main.activity_sondy_result.*
import kotlinx.android.synthetic.main.sondy_result.*

class SondiResultActivity : AppCompatActivity() {

    companion object {
        const val RESULT_DATA_PARAM = "result"
    }

    private lateinit var result: ScaleResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sondy_result)
        result = intent.getParcelableExtra(RESULT_DATA_PARAM)
        val vectors = result.scalesValues
        val vectorH: String = when {
            vectors[0] < 2 -> "Тенденция любви к человечеству, гуманизму. Тяга к культуре, любовь к природе"
            vectors[0] in 2 until 4 -> "Нежность, податливость, мягкость характера"
            vectors[0] > 3 -> "Тенденция к персональной любви и нежности"
            else -> ""
        }
        setSupportActionBar(toolbar)
        with(supportActionBar!!) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
            setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        }

        val vectorS: String = when {
            vectors[1] < 2 -> "Цивилизованность, пассивность, покорность, готовность к самопожертвованию, благородство"
            vectors[1] >= 2 -> "Мужество, активность, агрессивность, садизм, жестокость, предприимчивость, настойчивость"
            else -> ""
        }
        val vectorE: String = when {
            vectors[0] < 2 -> "Накапливание гнева, ревность, нетерпимость, ненависть, злобность, мстительность, злопамятность"
            vectors[0] >= 2 -> "Доброта, справедливость, терпимость, мягкость, готовность помочь другому, простодушие, доброжелательность"
            else -> ""
        }
        val vectorHy: String = when {
            vectors[0] < 2 -> "Скромность, моральная цензура, склонность к фантазированию, стыд, лживость, робость, трусость"
            vectors[0] >= 2 -> "Демонстративность, стремление произвести впечатление на окружающих, артистизм, жажда одобрения, честолюбие"
            else -> ""
        }
        val vectorK: String = when {
            vectors[0] < 2 -> "Страсть к отрицанию, деструкция, самоотрешенность, стремление приспособиться к коллективу"
            vectors[0] >= 2 -> "Эгоцентризм, педантизм, обидчивость, склонность к логически разумным процессам, упрямство"
            else -> ""
        }
        val vectorP: String = when {
            vectors[0] < 2 -> "Заниженная самооценка, осторожность, подозрительность, ранимость, недоверие, щепетильность"
            vectors[0] >= 2 -> "Стремление к лидерству, соперничеству; завышенной самооценка, пылкость, переоценка себя, энтузиазм"
            else -> ""
        }
        val vectorD: String = when {
            vectors[0] < 2 -> "Верность, жадность, инертность"
            vectors[0] >= 2 -> "Любопытство, неуверенность, непостоянство"
            else -> ""
        }
        val vectorM: String = when {
            vectors[0] < 2 -> "Тенденции к одиночеству и самостоятельности, суетливость, "
            vectors[0] >= 2 -> "Стремления к наслаждению, веселья, присутствия в группе, непостоянство"
            else -> ""
        }
        scale_1_value.text = vectorH
        scale_2_value.text = vectorS
        scale_3_value.text = vectorE
        scale_4_value.text = vectorHy
        scale_5_value.text = vectorK
        scale_6_value.text = vectorP
        scale_7_value.text = vectorD
        scale_8_value.text = vectorM
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sondy_result_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_share -> {
                val shareIntentText = "Пройден тест Сонди в приложении '${getString(R.string.app_name)}': " +
                        "${scale_1.text} : ${scale_1_value.text.toString().toLowerCase()}. " +
                        "${scale_2.text} : ${scale_2_value.text.toString().toLowerCase()}. " +
                        "${scale_3.text} : ${scale_3_value.text.toString().toLowerCase()}. " +
                        "${scale_4.text} : ${scale_4_value.text.toString().toLowerCase()}. " +
                        "${scale_5.text} : ${scale_5_value.text.toString().toLowerCase()}. " +
                        "${scale_6.text} : ${scale_6_value.text.toString().toLowerCase()}. " +
                        "${scale_7.text} : ${scale_7_value.text.toString().toLowerCase()}. " +
                        "${scale_8.text} : ${scale_8_value.text.toString().toLowerCase()}. "
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareIntentText)
                }
                startActivity(shareIntent)
                return true
            }
            R.id.menu_description -> {
                val intent = Intent(this, TestDescriptionActivity::class.java)
                        .putExtra(TestDescriptionActivity.TEST_ID_PARAM, result.id)
                startActivity(intent)
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
