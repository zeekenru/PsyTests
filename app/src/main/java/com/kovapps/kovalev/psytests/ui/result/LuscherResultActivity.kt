package com.kovapps.kovalev.psytests.ui.result

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.enities.LuscherResult
import com.kovapps.kovalev.psytests.enities.LusherColor

class LuscherResultActivity : AppCompatActivity() {

    companion object {
        const val RESULT_DATA_PARAM = "result"
    }

    private lateinit var closeBtn: ImageView
    private lateinit var scale1Result: TextView
    private lateinit var scale2Result: TextView
    private lateinit var scale3Result: TextView
    private lateinit var scale4Result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_luscher_result)
        closeBtn = findViewById(R.id.close_btn)
        scale1Result = findViewById(R.id.first_scale_result)
        scale2Result = findViewById(R.id.second_scale_result)
        scale3Result = findViewById(R.id.third_scale_result)
        scale4Result = findViewById(R.id.scale_4_result)
        closeBtn.setOnClickListener { finish() }
        val result: LuscherResult = intent.getParcelableExtra(RESULT_DATA_PARAM)!!
        val secondAnswers = result.answers
        val colorPositionValueText = StringBuilder()
        if (secondAnswers[0] == LusherColor.BlUE || secondAnswers[1] == LusherColor.BlUE) colorPositionValueText.append(
            getString(R.string.luscher_result_1).replaceFirstChar { it.uppercase() }).append(" ")
        if (secondAnswers[0] == LusherColor.GREEN || secondAnswers[1] == LusherColor.GREEN) colorPositionValueText.append(
            getString(
                R.string.luscher_result_2
            ).replaceFirstChar { it.uppercase() }).append(" ")
        if (secondAnswers[0] == LusherColor.RED || secondAnswers[1] == LusherColor.RED) colorPositionValueText.append(
            getString(
                R.string.luscher_result_3
            ).replaceFirstChar { it.uppercase() }).append(" ")
        if (secondAnswers[0] == LusherColor.YELLOW || secondAnswers[1] == LusherColor.YELLOW) colorPositionValueText.append(
            getString(
                R.string.luscher_result_4
            )
        ).append(" ")
        if (secondAnswers[2] == LusherColor.BlUE || secondAnswers[3] == LusherColor.BlUE) colorPositionValueText.append(
            getString(
                R.string.luscher_result_5
            )
        ).append(" ")
        if (secondAnswers[2] == LusherColor.GREEN || secondAnswers[3] == LusherColor.GREEN) colorPositionValueText.append(
            getString(
                R.string.luscher_result_6
            )
        ).append(" ")
        if (secondAnswers[2] == LusherColor.RED || secondAnswers[3] == LusherColor.RED) colorPositionValueText.append(
            getString(
                R.string.luscher_result_7
            )
        ).append(" ")
        if (secondAnswers[2] == LusherColor.YELLOW || secondAnswers[3] == LusherColor.YELLOW) colorPositionValueText.append(
            getString(
                R.string.luscher_result_8
            )
        ).append(" ")
        if (secondAnswers[4] == LusherColor.BlUE || secondAnswers[5] == LusherColor.YELLOW) colorPositionValueText.append(
            getString(
                R.string.luscher_result_9
            )
        ).append(" ")
        if (secondAnswers[4] == LusherColor.GREEN || secondAnswers[5] == LusherColor.YELLOW) colorPositionValueText.append(
            getString(
                R.string.luscher_result_10
            )
        ).append(" ")
        if (secondAnswers[4] == LusherColor.RED || secondAnswers[5] == LusherColor.YELLOW) colorPositionValueText.append(
            getString(
                R.string.luscher_result_11
            )
        ).append(" ")
        if (secondAnswers[4] == LusherColor.YELLOW || secondAnswers[5] == LusherColor.YELLOW) colorPositionValueText.append(
            getString(
                R.string.luscher_result_12
            )
        ).append(" ")
        if (secondAnswers[6] == LusherColor.BlUE || secondAnswers[7] == LusherColor.YELLOW) colorPositionValueText.append(
            getString(
                R.string.luscher_result_13
            )
        ).append(" ")
        if (secondAnswers[6] == LusherColor.GREEN || secondAnswers[7] == LusherColor.YELLOW) colorPositionValueText.append(
            getString(
                R.string.luscher_result_14
            )
        ).append(" ")
        if (secondAnswers[6] == LusherColor.RED || secondAnswers[7] == LusherColor.YELLOW) colorPositionValueText.append(
            getString(
                R.string.luscher_result_15
            )
        )
        if (secondAnswers[6] == LusherColor.YELLOW || secondAnswers[7] == LusherColor.YELLOW) colorPositionValueText.append(
            getString(
                R.string.luscher_result_16
            )
        ).append(" ")

        if (secondAnswers[0] == LusherColor.GRAY || secondAnswers[1] == LusherColor.GRAY) colorPositionValueText.append(
            getString(
                R.string.luscher_result_17
            )
        )
        if (secondAnswers[0] == LusherColor.PURPLE || secondAnswers[1] == LusherColor.PURPLE) colorPositionValueText.append(
            getString(
                R.string.luscher_result_18
            )
        )
        if (secondAnswers[0] == LusherColor.BROWN || secondAnswers[1] == LusherColor.BROWN) colorPositionValueText.append(
            getString(
                R.string.luscher_result_19
            )
        )
        if (secondAnswers[0] == LusherColor.BLACK || secondAnswers[1] == LusherColor.BLACK) colorPositionValueText.append(
            getString(
                R.string.luscher_result_20
            )
        )

        if (secondAnswers[2] == LusherColor.GRAY || secondAnswers[3] == LusherColor.GRAY) colorPositionValueText.append(
            getString(
                R.string.luscher_result_21
            )
        )
        if (secondAnswers[2] == LusherColor.PURPLE || secondAnswers[3] == LusherColor.PURPLE) colorPositionValueText.append(
            getString(
                R.string.luscher_result_22
            )
        )
        if (secondAnswers[2] == LusherColor.BROWN || secondAnswers[3] == LusherColor.BROWN) colorPositionValueText.append(
            getString(
                R.string.luscher_result_23
            )
        )
        if (secondAnswers[2] == LusherColor.BLACK || secondAnswers[3] == LusherColor.BLACK) colorPositionValueText.append(
            getString(
                R.string.luscher_result_24
            )
        )

        if (secondAnswers[4] == LusherColor.GRAY || secondAnswers[5] == LusherColor.GRAY) colorPositionValueText.append(
            getString(
                R.string.luscher_result_25
            )
        )
        if (secondAnswers[4] == LusherColor.PURPLE || secondAnswers[5] == LusherColor.PURPLE) colorPositionValueText.append(
            getString(
                R.string.luscher_result_26
            )
        )
        if (secondAnswers[4] == LusherColor.BROWN || secondAnswers[5] == LusherColor.BROWN) colorPositionValueText.append(
            getString(
                R.string.luscher_result_27
            )
        )
        if (secondAnswers[4] == LusherColor.BLACK || secondAnswers[5] == LusherColor.BLACK) colorPositionValueText.append(
            getString(
                R.string.luscher_result_28
            )
        )

        if (secondAnswers[6] == LusherColor.GRAY || secondAnswers[7] == LusherColor.GRAY) colorPositionValueText.append(
            getString(
                R.string.luscher_result_29
            )
        )
        if (secondAnswers[6] == LusherColor.PURPLE || secondAnswers[7] == LusherColor.PURPLE) colorPositionValueText.append(
            getString(
                R.string.luscher_result_30
            )
        )
        if (secondAnswers[6] == LusherColor.BROWN || secondAnswers[7] == LusherColor.BROWN) colorPositionValueText.append(
            getString(
                R.string.luscher_result_31
            )
        )
        if (secondAnswers[6] == LusherColor.BLACK || secondAnswers[7] == LusherColor.BLACK) colorPositionValueText.append(
            getString(
                R.string.luscher_result_32
            )
        )

        val firstGroup: String = when (secondAnswers[0]) {
            LusherColor.BlUE -> {
                when (secondAnswers[1]) {
                    LusherColor.GREEN -> getString(R.string.luscher_result_33).replaceFirstChar { it.uppercase() }
                    LusherColor.RED -> getString(R.string.luscher_result_34).replaceFirstChar { it.uppercase() }
                    LusherColor.PURPLE -> getString(R.string.luscher_result_35).replaceFirstChar { it.uppercase() }
                    LusherColor.BROWN -> getString(R.string.luscher_result_36).replaceFirstChar { it.uppercase() }
                    LusherColor.BLACK -> getString(R.string.luscher_result_37).replaceFirstChar { it.uppercase() }
                    LusherColor.GRAY -> getString(R.string.luscher_result_38).replaceFirstChar { it.uppercase() }
                    else -> getString(R.string.luscher_result_39).replaceFirstChar { it.uppercase() }
                }
            }
            LusherColor.GREEN -> {
                when (secondAnswers[1]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_40).replaceFirstChar { it.uppercase() }
                    LusherColor.RED -> getString(R.string.luscher_result_41).replaceFirstChar { it.uppercase() }
                    LusherColor.YELLOW -> getString(R.string.luscher_result_42).replaceFirstChar { it.uppercase() }
                    LusherColor.PURPLE -> getString(R.string.luscher_result_43).replaceFirstChar { it.uppercase() }
                    LusherColor.BROWN -> getString(R.string.luscher_result_44).replaceFirstChar { it.uppercase() }
                    LusherColor.BLACK -> getString(R.string.luscher_result_46).replaceFirstChar { it.uppercase() }
                    LusherColor.GRAY -> getString(R.string.luscher_result_45).replaceFirstChar { it.uppercase() }
                    else -> getString(R.string.luscher_result_47).replaceFirstChar { it.uppercase() }
                }
            }
            LusherColor.RED -> {
                when (secondAnswers[1]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_48)
                    LusherColor.GREEN -> getString(R.string.luscher_result_49)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_50)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_51)
                    LusherColor.BROWN -> getString(R.string.luscher_result_52)
                    LusherColor.BLACK -> getString(R.string.luscher_result_53)
                    LusherColor.GRAY -> getString(R.string.luscher_result_54)
                    else -> getString(R.string.luscher_result_55)
                }
            }
            LusherColor.YELLOW -> {
                when (secondAnswers[1]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_56)
                    LusherColor.GREEN -> getString(R.string.luscher_result_57)
                    LusherColor.RED -> getString(R.string.luscher_result_58)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_59)
                    LusherColor.BROWN -> getString(R.string.luscher_result_60)
                    LusherColor.BLACK -> getString(R.string.luscher_result_61)
                    LusherColor.GRAY -> getString(R.string.luscher_result_62)
                    else -> getString(R.string.luscher_result_63)
                }
            }
            LusherColor.PURPLE -> {
                when (secondAnswers[1]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_64)
                    LusherColor.GREEN -> getString(R.string.luscher_result_65)
                    LusherColor.RED -> getString(R.string.luscher_result_66)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_67)
                    LusherColor.BROWN -> getString(R.string.luscher_result_68)
                    LusherColor.BLACK -> getString(R.string.luscher_result_69)
                    LusherColor.GRAY -> getString(R.string.luscher_result_70)
                    else -> getString(R.string.luscher_result_71)
                }
            }
            LusherColor.BROWN -> {
                when (secondAnswers[1]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_72)
                    LusherColor.GREEN -> getString(R.string.luscher_result_73)
                    LusherColor.RED -> getString(R.string.luscher_result_74)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_75)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_76)
                    LusherColor.BLACK -> getString(R.string.luscher_result_77)
                    LusherColor.GRAY -> getString(R.string.luscher_result_78)
                    else -> getString(R.string.luscher_result_79)
                }
            }
            LusherColor.BLACK -> {
                when (secondAnswers[1]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_80)
                    LusherColor.GREEN -> getString(R.string.luscher_result_81)
                    LusherColor.RED -> getString(R.string.luscher_result_82)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_83)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_84)
                    LusherColor.BROWN -> getString(R.string.luscher_result_85)
                    LusherColor.GRAY -> getString(R.string.luscher_result_86)
                    else -> getString(R.string.luscher_result_87)
                }
            }
            LusherColor.GRAY -> {
                when (secondAnswers[1]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_88)
                    LusherColor.GREEN -> getString(R.string.luscher_result_89)
                    LusherColor.RED -> getString(R.string.luscher_result_90)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_91)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_92)
                    LusherColor.BROWN -> getString(R.string.luscher_result_93)
                    LusherColor.BLACK -> getString(R.string.luscher_result_94)
                    else -> getString(R.string.luscher_result_95)
                }
            }
        }

        val thirdGroup: String = when (secondAnswers[2]) {
            LusherColor.GRAY -> {
                when (secondAnswers[3]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_96)
                    LusherColor.GREEN -> getString(R.string.luscher_result_97)
                    LusherColor.RED -> getString(R.string.luscher_result_98)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_99)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_100)
                    LusherColor.BROWN -> getString(R.string.luscher_result_101)
                    LusherColor.BLACK -> getString(R.string.luscher_result_102)
                    else -> getString(R.string.luscher_result_103)
                }
            }
            LusherColor.GREEN -> {
                when (secondAnswers[3]) {
                    LusherColor.GRAY -> getString(R.string.luscher_result_104)
                    LusherColor.BlUE -> getString(R.string.luscher_result_105)
                    LusherColor.RED -> getString(R.string.luscher_result_106)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_107)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_108)
                    LusherColor.BROWN -> getString(R.string.luscher_result_109)
                    LusherColor.BLACK -> getString(R.string.luscher_result_110)
                    else -> getString(R.string.luscher_result_111)
                }
            }
            LusherColor.BlUE -> {
                when (secondAnswers[3]) {
                    LusherColor.GRAY -> getString(R.string.luscher_result_112)
                    LusherColor.GREEN -> getString(R.string.luscher_result_113)
                    LusherColor.RED -> getString(R.string.luscher_result_114)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_115)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_116)
                    LusherColor.BROWN -> getString(R.string.luscher_result_117)
                    LusherColor.BLACK -> getString(R.string.luscher_result_118)
                    else -> getString(R.string.luscher_result_119)
                }
            }
            LusherColor.RED -> {
                when (secondAnswers[3]) {
                    LusherColor.GRAY -> getString(R.string.luscher_result_120)
                    LusherColor.BlUE -> getString(R.string.luscher_result_121)
                    LusherColor.GREEN -> getString(R.string.luscher_result_122)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_123)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_124)
                    LusherColor.BROWN -> getString(R.string.luscher_result_125)
                    LusherColor.BLACK -> getString(R.string.luscher_result_126)
                    else -> getString(R.string.luscher_result_127)
                }
            }
            LusherColor.BLACK -> {
                when (secondAnswers[3]) {
                    LusherColor.GRAY -> getString(R.string.luscher_result_128)
                    LusherColor.GREEN -> getString(R.string.luscher_result_129)
                    LusherColor.RED -> getString(R.string.luscher_result_130)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_131)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_132)
                    LusherColor.BROWN -> getString(R.string.luscher_result_133)
                    LusherColor.BlUE -> getString(R.string.luscher_result_134)
                    else -> getString(R.string.luscher_result_135)
                }
            }
            LusherColor.BROWN -> {
                when (secondAnswers[3]) {
                    LusherColor.GRAY -> getString(R.string.luscher_result_136)
                    LusherColor.GREEN -> getString(R.string.luscher_result_137)
                    LusherColor.RED -> getString(R.string.luscher_result_138)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_139)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_140)
                    LusherColor.BlUE -> getString(R.string.luscher_result_141)
                    LusherColor.BLACK -> getString(R.string.luscher_result_142)
                    else -> getString(R.string.luscher_result_143)
                }
            }
            LusherColor.YELLOW -> {
                when (secondAnswers[3]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_144)
                    LusherColor.GREEN -> getString(R.string.luscher_result_145)
                    LusherColor.RED -> getString(R.string.luscher_result_146)
                    LusherColor.GRAY -> getString(R.string.luscher_result_147)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_148)
                    LusherColor.BROWN -> getString(R.string.luscher_result_149)
                    LusherColor.BLACK -> getString(R.string.luscher_result_150)
                    else -> getString(R.string.luscher_result_151)
                }
            }
            LusherColor.PURPLE -> {
                when (secondAnswers[3]) {
                    LusherColor.GRAY -> getString(R.string.luscher_result_152)
                    LusherColor.GREEN -> getString(R.string.luscher_result_153)
                    LusherColor.RED -> getString(R.string.luscher_result_154)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_155)
                    LusherColor.BlUE -> getString(R.string.luscher_result_156)
                    LusherColor.BROWN -> getString(R.string.luscher_result_157)
                    LusherColor.BLACK -> getString(R.string.luscher_result_158)
                    else -> getString(R.string.luscher_result_159)
                }
            }
        }

        val secondGroup: String = when (secondAnswers[6]) {
            LusherColor.GRAY -> {
                when (secondAnswers[7]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_160)
                    LusherColor.GREEN -> getString(R.string.luscher_result_161)
                    LusherColor.RED -> getString(R.string.luscher_result_162)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_163)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_164)
                    LusherColor.BROWN -> getString(R.string.luscher_result_165)
                    LusherColor.BLACK -> getString(R.string.luscher_result_166)
                    else -> getString(R.string.luscher_result_167)
                }
            }
            LusherColor.BlUE -> {
                when (secondAnswers[7]) {
                    LusherColor.GRAY -> getString(R.string.luscher_result_168)
                    LusherColor.GREEN -> getString(R.string.luscher_result_169)
                    LusherColor.RED -> getString(R.string.luscher_result_170)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_171)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_172)
                    LusherColor.BROWN -> getString(R.string.luscher_result_173)
                    LusherColor.BLACK -> getString(R.string.luscher_result_174)
                    else -> getString(R.string.luscher_result_175)
                }
            }
            LusherColor.GREEN -> {
                when (secondAnswers[7]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_176)
                    LusherColor.GRAY -> getString(R.string.luscher_result_177)
                    LusherColor.RED -> getString(R.string.luscher_result_178)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_179)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_180)
                    LusherColor.BROWN -> getString(R.string.luscher_result_181)
                    LusherColor.BLACK -> getString(R.string.luscher_result_182)
                    else -> getString(R.string.luscher_result_183)
                }
            }
            LusherColor.RED -> {
                when (secondAnswers[7]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_184)
                    LusherColor.GREEN -> getString(R.string.luscher_result_185)
                    LusherColor.GRAY -> getString(R.string.luscher_result_186)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_187)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_188)
                    LusherColor.BROWN -> getString(R.string.luscher_result_189)
                    LusherColor.BLACK -> getString(R.string.luscher_result_190)
                    else -> getString(R.string.luscher_result_191)
                }
            }
            LusherColor.YELLOW -> {
                when (secondAnswers[7]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_192)
                    LusherColor.GREEN -> getString(R.string.luscher_result_193)
                    LusherColor.RED -> getString(R.string.luscher_result_194)
                    LusherColor.GRAY, LusherColor.PURPLE -> getString(R.string.luscher_result_195)
                    LusherColor.BROWN, LusherColor.BLACK -> getString(R.string.luscher_result_196)
                    else -> getString(R.string.luscher_result_197)
                }
            }
            LusherColor.PURPLE -> {
                when (secondAnswers[7]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_198)
                    LusherColor.GREEN -> getString(R.string.luscher_result_199)
                    LusherColor.RED -> getString(R.string.luscher_result_200)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_201)
                    LusherColor.GRAY -> getString(R.string.luscher_result_202)
                    LusherColor.BROWN -> getString(R.string.luscher_result_203)
                    LusherColor.BLACK -> getString(R.string.luscher_result_204)
                    else -> getString(R.string.luscher_result_205)
                }
            }
            LusherColor.BROWN -> {
                when (secondAnswers[7]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_206)
                    LusherColor.GREEN -> getString(R.string.luscher_result_207)
                    LusherColor.RED -> getString(R.string.luscher_result_208)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_209)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_210)
                    LusherColor.GRAY -> getString(R.string.luscher_result_211)
                    LusherColor.BLACK -> getString(R.string.luscher_result_212)
                    else -> getString(R.string.luscher_result_213)
                }
            }
            LusherColor.BLACK -> {
                when (secondAnswers[7]) {
                    LusherColor.BlUE -> getString(R.string.luscher_result_214)
                    LusherColor.GREEN -> getString(R.string.luscher_result_215)
                    LusherColor.RED -> getString(R.string.luscher_result_216)
                    LusherColor.YELLOW -> getString(R.string.luscher_result_217)
                    LusherColor.PURPLE -> getString(R.string.luscher_result_218)
                    LusherColor.BROWN -> getString(R.string.luscher_result_219)
                    LusherColor.BLACK -> getString(R.string.luscher_result_220)
                    else -> getString(R.string.luscher_result_221)
                }
            }

        }
        scale1Result.text = firstGroup
        scale2Result.text = secondGroup
        scale3Result.text = colorPositionValueText.toString()
        scale4Result.text = thirdGroup
    }
}
