package com.kovapps.kovalev.psytests.ui.history


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.TestsTypes
import com.kovapps.kovalev.psytests.callbacks.SwipeToDeleteCallback
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.enities.*
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.result.*
import toothpick.Toothpick
import javax.inject.Inject


class HistoryFragment : Fragment() {


    init {
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
    }

    @Inject
    lateinit var dao: TestDao

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private lateinit var recyclerView: RecyclerView

    private lateinit var emptyHistoryView: ViewStub

    private lateinit var backBtn: View

    private lateinit var deleteAllBtn: View

    private lateinit var titleText: View

    private lateinit var items: List<HistoryItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.history_recycler_view)
        emptyHistoryView = view.findViewById(R.id.empty_history_view)
        backBtn = view.findViewById(R.id.history_back_btn)
        deleteAllBtn = view.findViewById(R.id.history_delete_all_btn)
        titleText = view.findViewById(R.id.history_text)
        backBtn.setOnClickListener {
            if (activity != null) {
                requireActivity().onBackPressed()
            }
        }
        deleteAllBtn.setOnClickListener {
            showDeleteDialog()
        }
        with(recyclerView) {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        items = dao.getHistory().reversed()
        if (items.isEmpty()) {
            showEmptyStub(true)

        } else {
            showEmptyStub(false)
            showHistory(items)
        }
    }


    private fun showDeleteDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_all_results)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                dialog.dismiss()
                (recyclerView.adapter as HistoryItemsAdapter).deleteAll()
                dao.deleteAllHistory()
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.cancel()
            }
            .create()
        dialog.show()
    }

    private fun showHistory(items: List<HistoryItem>) {
        val clickListener = { result: HistoryItem ->
            val intent = when (result.testId) {
                TestsTypes.BECK_DEPRESSION, TestsTypes.ZUNG_DEPRESSION,
                TestsTypes.BECK_HOPELESSNESS, TestsTypes.ZUNG_ANXIETY, TestsTypes.KARPOV_REFLECTION, TestsTypes.BRETMEN,
                TestsTypes.OUB, TestsTypes.EQ, TestsTypes.TEST_14, TestsTypes.TEST_15, TestsTypes.TEST_16, TestsTypes.TEST_17, TestsTypes.TEST_21 ->
                    Intent(context, OneScaleResultActivity::class.java)
                        .putExtra(
                            OneScaleResultActivity.RESULT_DATA_PARAM,
                            result as OneScaleResult
                        )
                TestsTypes.SONDY -> Intent(context, SondiResultActivity::class.java)
                    .putExtra(SondiResultActivity.RESULT_DATA_PARAM, result as ScaleResult)
                TestsTypes.LUSHER -> Intent(context, LuscherResultActivity::class.java)
                    .putExtra(LuscherResultActivity.RESULT_DATA_PARAM, result as LuscherResult)
                TestsTypes.BASS -> Intent(context, BassResultActivity::class.java)
                    .putExtra(BassResultActivity.RESULT_DATA_PARAM, result as ScaleResult)
                TestsTypes.TEST_20 -> Intent(context, Test20ResultActivity::class.java)
                    .putExtra(Test20ResultActivity.RESULT_DATA_PARAM, result as ScaleResult)
                TestsTypes.MASLACH, TestsTypes.EPI -> Intent(
                    context,
                    ThreeScalesResultActivity::class.java
                )
                    .putExtra(
                        ThreeScalesResultActivity.RESULT_DATA_PARAM,
                        result as ThreeScalesResult
                    )
                TestsTypes.TEST_18 -> Intent(context, FiveScalesResultActivity::class.java)
                    .putExtra(FiveScalesResultActivity.RESULT_DATA_PARAM, result as ScaleResult)
                TestsTypes.OST -> Intent(context, OstResultActivity::class.java)
                    .putExtra(OstResultActivity.RESULT_DATA_PARAM, result as ScaleResult)
                TestsTypes.TEST_22 -> Intent(context, Test22ResultActivity::class.java)
                    .putExtra(Test22ResultActivity.RESULT_DATA_PARAM, result as ScaleResult)
                in (23..50) -> Intent(context, SimpleTestResultActivity::class.java)
                    .putExtra(
                        SimpleTestResultActivity.RESULT_DATA_PARAM,
                        result as SimpleTestResult
                    )
                else -> throw IllegalArgumentException("Unexpected result")
            }
            startActivity(intent)
        }
        val onEmptyHistoryListener = { isEmpty: Boolean ->
            if (isEmpty) {
                setMenuVisibility(false)
                if (emptyHistoryView.isInLayout) {
                    emptyHistoryView.visibility = View.VISIBLE
                } else {
                    showEmptyStub(true)
                }
            } else {
                showEmptyStub(false)
            }
        }

        recyclerView.adapter = HistoryItemsAdapter(
            items.sortedWith(compareBy { it.date }).toMutableList(),
            clickListener,
            onEmptyHistoryListener
        )
        ItemTouchHelper(object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                dao.deleteFromHistory(items[viewHolder.adapterPosition].testId)
                items.toMutableList().removeAt(viewHolder.adapterPosition)
                (recyclerView.adapter as HistoryItemsAdapter).deleteItem(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(recyclerView)

    }

    private fun showEmptyStub(show: Boolean) {
        if (show) {
            titleText.visibility = View.INVISIBLE
            deleteAllBtn.visibility = View.INVISIBLE
            emptyHistoryView.inflate()
        } else {
            titleText.visibility = View.VISIBLE
            emptyHistoryView.visibility = View.GONE
            deleteAllBtn.visibility = View.VISIBLE
        }

    }


}
