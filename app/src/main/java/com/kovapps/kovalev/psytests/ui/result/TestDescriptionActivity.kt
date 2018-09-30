package com.kovapps.kovalev.psytests.ui.result

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.text.method.ScrollingMovementMethod
import android.widget.ScrollView
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.model.TestDao
import kotlinx.android.synthetic.main.activity_test_description.*
import toothpick.Toothpick
import javax.inject.Inject

class TestDescriptionActivity : AppCompatActivity() {

    companion object {
        const val TEST_ID_PARAM = "test_id"
    }

    @Inject
    lateinit var testDao: TestDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_description)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        val markdownText = testDao.getTestDescriptionById(intent.getIntExtra(TEST_ID_PARAM, 1))
        markdown_view.markdown = markdownText
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp)
        with(supportActionBar!!) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        var oldScrollPosition = 0
        scroll_view.setOnScrollChangeListener { view: NestedScrollView?, x: Int, y: Int, oldX: Int, oldY: Int ->
            if (scroll_view.scrollY > oldScrollPosition) fab.hide()
            else if (scroll_view.scrollY < oldScrollPosition || scroll_view.scrollY <= 0) fab.show()
            oldScrollPosition = scroll_view.scrollY;
        }
        markdown_view.textSize = 18F
        markdown_view.movementMethod = ScrollingMovementMethod()
        fab.setOnClickListener { scroll_view.fullScroll(ScrollView.FOCUS_UP) }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
