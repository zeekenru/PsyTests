package com.kovapps.kovalev.psytests.ui.home


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
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
import com.kovapps.kovalev.psytests.ui.test.LusherActivity
import com.kovapps.kovalev.psytests.ui.test.BaseActivity
import com.kovapps.kovalev.psytests.ui.test.SondiActivity
import kotlinx.android.synthetic.main.fragment_home.*
import toothpick.Toothpick
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject
    lateinit var dao: TestDao

    companion object {
        private const val DESCRIPTION_DIALOG_TAG = "description_dialog"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(tests_recycler_view) {
            layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        }
        showTests(dao.getAllTests())
    }

    private fun showTests(tests: List<Test>) {
        tests_recycler_view.adapter = TestsAdapter(tests, { handleClick(it.id) },
                { showDescription(it.description!!) }, resources.getBoolean(R.bool.isTablet))
    }

    private fun handleClick(id: Int) {
        when (id) {
            TestsTypes.LUSHER -> openLusherTest()
            TestsTypes.SONDY -> openSondyTest()
            else -> openBaseTest(dao.getTestById(id))
        }
    }

    private fun openSondyTest() {
        startActivity(Intent(context, SondiActivity::class.java))
    }

    private fun openBaseTest(test: Test) {
        val intent = Intent(context, BaseActivity::class.java)
                .putExtra(BaseActivity.TEST_PARAM, test)
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
        dialogFragment.show(fragmentManager, DESCRIPTION_DIALOG_TAG)
    }
}
