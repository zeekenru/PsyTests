package com.kovapps.kovalev.psytests.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kovapps.kovalev.psytests.Categories.CAREER
import com.kovapps.kovalev.psytests.Categories.FUN
import com.kovapps.kovalev.psytests.Categories.NEW
import com.kovapps.kovalev.psytests.Categories.PSY
import com.kovapps.kovalev.psytests.Categories.RELATIONSHIPS
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.BaseTest
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.home.TestsAdapter
import toothpick.Toothpick
import javax.inject.Inject

const val CATEGORY_ARG = "category"
private const val DETAIL_FM_TAG = "detail"

class CategoryFeedFragment : Fragment() {


    @Inject
    lateinit var db: TestDao

    private var selectedCategory: Int = 0

    private lateinit var categoryText: TextView
    private lateinit var backBtn: ImageView
    private lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            selectedCategory = it.getInt(CATEGORY_ARG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        return inflater.inflate(R.layout.fragment_category_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryText = view.findViewById(R.id.category_text)
        backBtn = view.findViewById(R.id.category_back_btn)
        recyclerview = view.findViewById(R.id.category_recyclerview)
        with(recyclerview) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        categoryText.text = when (selectedCategory) {
            PSY -> getString(R.string.category_psy)
            CAREER -> getString(R.string.category_career)
            RELATIONSHIPS -> getString(R.string.category_relationships)
            FUN -> getString(R.string.category_fun)
            NEW -> getString(R.string.new_tests)
            else -> getString(R.string.tests)
        }
        backBtn.setOnClickListener {
            if (activity != null) {
                requireActivity().onBackPressed()
            }
        }
        showTests()
    }

    private fun showTests() {
        recyclerview.adapter =
            TestsAdapter(db.getTestByCategory(selectedCategory)) { handleClick(it) }
    }

    private fun handleClick(test: BaseTest) {
        TestDetailFragment.newInstance(test)
            .showNow(requireActivity().supportFragmentManager, DETAIL_FM_TAG)
    }

    companion object {

        @JvmStatic
        fun newInstance(category: Int) =
            CategoryFeedFragment().apply {
                arguments = Bundle().apply {
                    putInt(CATEGORY_ARG, category)
                }
            }
    }
}