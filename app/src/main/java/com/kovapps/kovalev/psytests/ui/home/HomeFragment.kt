package com.kovapps.kovalev.psytests.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kovapps.kovalev.psytests.Categories
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.categories.CategoryFeedFragment
import com.kovapps.kovalev.psytests.ui.history.HistoryFragment
import com.kovapps.kovalev.psytests.ui.settings.SettingsFragment
import com.kovapps.kovalev.psytests.ui.test.BaseActivity
import com.kovapps.kovalev.psytests.ui.test.LusherActivity
import com.kovapps.kovalev.psytests.ui.test.SimpleTestActivity
import com.kovapps.kovalev.psytests.ui.test.SondiActivity
import toothpick.Toothpick
import javax.inject.Inject

private const val SETTINGS_FM_TAG = "settings"
private const val HISTORY_FM_TAG = "history"
private const val CATEGORY_FM_TAG = "category"

class HomeFragment : Fragment() {

    private lateinit var recommendedCard: View
    private lateinit var newCard: View
    private lateinit var psyCard: View
    private lateinit var settingsCard: View
    private lateinit var historyCard: View
    private lateinit var relationshipCard: View
    private lateinit var careerCard: View
    private lateinit var completedTestsValueText: TextView
    private lateinit var recommendedTestName: TextView
    private lateinit var completedTestsProgressBar: ProgressBar
    private var recommendedTestId = 0

    @Inject
    lateinit var dao: TestDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        with(view) {
            recommendedCard = findViewById(R.id.recommended_card)
            newCard = findViewById(R.id.new_tests_card)
            psyCard = findViewById(R.id.psy_card)
            settingsCard = findViewById(R.id.settings_card)
            historyCard = findViewById(R.id.results_card)
            relationshipCard = findViewById(R.id.relationships_card)
            careerCard = findViewById(R.id.career_card)
            completedTestsValueText = findViewById(R.id.completed_tests_value)
            completedTestsProgressBar = findViewById(R.id.home_progress_bar)
            recommendedTestName = findViewById(R.id.top_test_name)

        }
        completedTestsProgressBar.progress = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            completedTestsProgressBar.min = 0
        }
        newCard.setOnClickListener {
            showCategoryFragment(Categories.NEW)
        }
        relationshipCard.setOnClickListener {
            showCategoryFragment(Categories.RELATIONSHIPS)
        }
        careerCard.setOnClickListener {
            showCategoryFragment(Categories.CAREER)
        }
        psyCard.setOnClickListener {
            showCategoryFragment(Categories.PSY)
        }
        settingsCard.setOnClickListener {
            showFragment(SettingsFragment.newInstance(), SETTINGS_FM_TAG)
        }
        historyCard.setOnClickListener {
            showFragment(HistoryFragment(), HISTORY_FM_TAG)
        }
        with(dao.getRandomTest()) {
            recommendedTestName.text = name
            recommendedTestId = id
        }
        recommendedCard.isEnabled = true
        recommendedCard.setOnClickListener {
            it.isEnabled = false
            when (recommendedTestId) {
                TestsTypes.LUSHER -> {
                    startActivity(Intent(context, LusherActivity::class.java))
                }
                TestsTypes.SONDY -> {
                    startActivity(Intent(context, SondiActivity::class.java))
                }
                in 23..50 -> {
                    val test = dao.getTestById(recommendedTestId)
                    val intent = Intent(activity, SimpleTestActivity::class.java)
                    intent.putExtra(SimpleTestActivity.TEST_PARAM, test)
                    startActivity(intent)
                }
                else -> {
                    val test = dao.getPsyTestById(recommendedTestId)
                    val intent = Intent(activity, BaseActivity::class.java)
                    intent.putExtra(BaseActivity.TEST_PARAM, test)
                    startActivity(intent)
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        recommendedCard.isEnabled = true
        val completedTests = dao.getCompletedTestsCount()
        completedTestsProgressBar.progress = completedTests
        completedTestsValueText.text = "$completedTests/${dao.getAllTests().size}"
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .addToBackStack(null).commit()
    }

    private fun showCategoryFragment(category: Int) {
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            CategoryFeedFragment.newInstance(category),
            CATEGORY_FM_TAG
        )
            .addToBackStack(null).commit()
    }


}