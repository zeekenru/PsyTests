package com.kovapps.kovalev.psytests.ui.home


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.dialogs.TestDescriptionFragment
import com.kovapps.kovalev.psytests.enities.Test
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.test.LusherActivity
import com.kovapps.kovalev.psytests.view.BaseActivity
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
        ad_view.loadAd(AdRequest.Builder().build())
        ad_view.adListener = object : AdListener(){
            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                ad_view.visibility = View.GONE
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                if (ad_view != null) ad_view.visibility = View.VISIBLE
            }

        }
        with(tests_recycler_view) {
            layoutManager = if (!resources.getBoolean(R.bool.isTablet)) {
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            } else GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }

        showTests(dao.getAllTests())
    }

    private fun showTests(tests: List<Test>) {
        tests_recycler_view.adapter = TestsAdapter(tests, { handleClick(it.id) },
                { showDescription(it.description!!) })
    }

    private fun handleClick(id: Int) {
        if (id == 10) {
            openLusherTest()
        } else openBaseTest(dao.getTestById(id))
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
