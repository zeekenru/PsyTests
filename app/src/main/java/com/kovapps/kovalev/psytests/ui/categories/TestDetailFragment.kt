package com.kovapps.kovalev.psytests.ui.categories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.BaseTest
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.test.BaseActivity
import com.kovapps.kovalev.psytests.ui.test.LusherActivity
import com.kovapps.kovalev.psytests.ui.test.SimpleTestActivity
import com.kovapps.kovalev.psytests.ui.test.SondiActivity
import toothpick.Toothpick
import javax.inject.Inject


const val TEST_ARG = "test"

class TestDetailFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var db: TestDao

    private var test: BaseTest? = null
    private lateinit var testName: TextView
    private lateinit var testDescription: TextView
    private lateinit var startBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            test = it.getParcelable(TEST_ARG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        return inflater.inflate(R.layout.fragment_test_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testName = view.findViewById(R.id.test_detail_name)
        testDescription = view.findViewById(R.id.test_detail_description)
        startBtn = view.findViewById(R.id.start_btn)
        if (test != null) {
            with(test) {
                testName.text = this!!.name.replaceFirstChar { it.uppercase() }
                if (description != null) {
                    testDescription.text = description.replaceFirstChar { it.uppercase() }
                }
                startBtn.setOnClickListener {
                    dismiss()
                    when (test!!.id) {
                        TestsTypes.LUSHER -> {
                            startActivity(Intent(context, LusherActivity::class.java))
                        }
                        TestsTypes.SONDY -> {
                            startActivity(Intent(context, SondiActivity::class.java))
                        }
                        in 23..50 -> {
                            val test = db.getTestById(id)
                            val intent = Intent(activity, SimpleTestActivity::class.java)
                            intent.putExtra(SimpleTestActivity.TEST_PARAM, test)
                            startActivity(intent)
                        }
                        else -> {
                            val test = db.getPsyTestById(id)
                            val intent = Intent(activity, BaseActivity::class.java)
                            intent.putExtra(BaseActivity.TEST_PARAM, test)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(test: BaseTest) =
            TestDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TEST_ARG, test)
                }
            }
    }
}