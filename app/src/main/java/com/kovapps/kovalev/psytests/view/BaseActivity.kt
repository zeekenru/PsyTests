package com.kovapps.kovalev.psytests.view

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.kovapps.kovalev.psytests.MenuItemDoubleClickListener
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.Test
import com.kovapps.kovalev.psytests.enities.TestUiViews
import com.tinsuke.icekick.extension.freezeInstanceState
import com.tinsuke.icekick.extension.serialState
import com.tinsuke.icekick.extension.unfreezeInstanceState
import toothpick.Toothpick
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        const val TEST_PARAM = "test"
    }

    private var firstBackClick: Boolean by serialState(true)

    private var questionsCount: Int by serialState(0)

    private var currentQuestion: Int by serialState(1)

    protected lateinit var test: Test

    private lateinit var views: TestUiViews

    @Inject
    lateinit var dao: TestDao

    @Inject lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            unfreezeInstanceState(savedInstanceState)
        } else {
            test = intent.getParcelableExtra(TEST_PARAM)
            questionsCount = test.questions.size
        }
        views = init()
        views.progressBar.progressDrawable.setColorFilter(ContextCompat.getColor(this, R.color.primaryColor),
                android.graphics.PorterDuff.Mode.SRC_IN)
        views.backNavigationIcon.setOnClickListener(object : MenuItemDoubleClickListener() {
            override fun onSingleClick(item: View) {
                if (firstBackClick || currentQuestion == 1) {
                    showBackToast()
                    firstBackClick = false
                } else {
                    back()
                }

            }

            override fun onDoubleClick(item: View) {
                finish()
            }
        })
    }

    protected abstract fun init(): TestUiViews

    protected abstract fun back()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        freezeInstanceState(outState)
    }

    protected abstract fun getLayoutResourceId(): Int

    protected abstract fun proceedTest(): Test

    protected abstract fun showResult()

    private fun showBackToast() {
        Toast.makeText(this@BaseActivity, getString(R.string.double_click_to_close),
                Toast.LENGTH_SHORT).show()
    }

    protected open fun showQuestion(currentQuestion: Int, questionsCount: Int) {
        if (currentQuestion > questionsCount) {
            showResult()
            finish()
        } else {
            views.questionsCounterView.text = "$currentQuestion/$questionsCount"
        }
    }
}
