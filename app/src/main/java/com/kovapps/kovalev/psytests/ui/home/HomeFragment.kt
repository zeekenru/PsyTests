package com.kovapps.kovalev.psytests.ui.home


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.dialogs.TestDescriptionFragment
import com.kovapps.kovalev.psytests.enities.Test
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.test.*
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_home.*
import toothpick.Toothpick
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject lateinit var dao : TestDao

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        return  inflater.inflate(R.layout.fragment_home, container, false)
    }


    private fun openYNTest(yesNoTest: Test) {
        val intent = Intent(context, YesNoTestActivity::class.java)
                .putExtra(YesNoTestActivity.TEST_PARAM, yesNoTest)
        startActivity(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(tests_recycler_view) {
            layoutManager = if (!resources.getBoolean(R.bool.isTablet)){
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false).apply {
                    Logger.d("Linear layout manager")
                }
            } else GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }

        showTests(dao.getAllTests())
    }

    private fun showTests(tests: List<Test>) {
        tests_recycler_view.adapter = TestsAdapter(tests, { handleClick(it.id)},
                { showDescription(it.description!!) })
    }

    private fun handleClick(id: Int) {
        when (id){
            TestsTypes.BECK_DEPRESSION -> openBeckTest(dao.getTestById(1))
            TestsTypes.ZUNG_DEPRESSION -> openETest(dao.getTestById(3))
            TestsTypes.BECK_HOPELESSNESS -> openETest(dao.getTestById(3))
            TestsTypes.ZUNG_ANXIETY -> openETest(dao.getTestById(4))
            TestsTypes.KARPOV_REFLECTION -> openKarpovTest(dao.getTestById(5))
            TestsTypes.MASLACH -> openKarpovTest(dao.getTestById(6))
            TestsTypes.BRETMEN -> openYNTest(dao.getTestById(7))
            TestsTypes.OUB -> openOubTest(dao.getTestById(8))
            TestsTypes.EPI -> openEpiTest(dao.getTestById(9))
            TestsTypes.LUSHER -> openLusherTest()
            TestsTypes.OST -> openYNTest(dao.getTestById(11))
            TestsTypes.EQ -> openETest(dao.getTestById(12))
        }
    }

    private fun openBeckTest(test: Test) {
        val intent = Intent(context, BeckDepressionActivity::class.java)
        intent.putExtra(BeckDepressionActivity.TEST_PARAM, test)
        startActivity(intent)
    }

    private fun openETest(test: Test) {
        val intent = Intent(context, TestEActivity::class.java)
        intent.putExtra(TestEActivity.TEST_PARAM, test)
        startActivity(intent)
    }

    private fun openOubTest(test: Test) {
        val intent = Intent(context, TestDActivity::class.java)
        intent.putExtra(TestDActivity.TEST_PARAM, test)
        startActivity(intent)
    }

    private fun openEpiTest(test: Test) {
        val intent = Intent(context, TestBActivity::class.java).putExtra(TestBActivity.TEST_PARAM, test)
        startActivity(intent)
    }

    private fun openKarpovTest(test: Test) {
        val intent = Intent(context, TestCActivity::class.java)
        intent.putExtra(TestCActivity.TEST_PARAM, test)
        startActivity(intent)
    }

    private fun openLusherTest() {
        startActivity(Intent(context, LusherActivity::class.java))
    }

    private fun showDescription(description: String) {
        val bundle = Bundle()
        bundle.putString(TestDescriptionFragment.DESCRIPTION_PARAM, description)
        val dialogFragment = TestDescriptionFragment()
        dialogFragment.arguments = bundle
        dialogFragment.show(fragmentManager, "tag")
    }
}
