package com.kovapps.kovalev.psytests.ui.history


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.SwipeToDeleteCallback
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.dialogs.DeleteAllDialogFragment
import com.kovapps.kovalev.psytests.enities.OneScaleResult
import com.kovapps.kovalev.psytests.enities.Result
import com.kovapps.kovalev.psytests.enities.ScaleResult
import com.kovapps.kovalev.psytests.enities.ThreeScalesResult
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.model.TestDao
import com.kovapps.kovalev.psytests.ui.result.OstResultActivity
import com.kovapps.kovalev.psytests.ui.result.OneScaleResultActivity
import com.kovapps.kovalev.psytests.ui.result.ThreeScalesResultActivity
import kotlinx.android.synthetic.main.fragment_history.*
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

    companion object {
        private const val DELETE_ALL_HISTORY_DIALOG_CODE = 1
        private const val DELETE_ALL_HISTORY_TAG = "delete_history"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(history_recycler_view) {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        if (!preferenceHelper.saveResultsEnabled()) {

        }
        val items = dao.getHistory()
        if (items.isEmpty()) {
            showEmptyHistory(true)
            setMenuVisibility(false)

        } else {
            showHistory(items)
            setMenuVisibility(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> {
                showDeleteDialog()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            DELETE_ALL_HISTORY_DIALOG_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    (history_recycler_view.adapter as HistoryItemsAdapter).deleteAll()
                    dao.deleteAllHistory()
                }
            }
        }
    }

    private fun showDeleteDialog() {
        val dialog = DeleteAllDialogFragment()
        dialog.setTargetFragment(this, DELETE_ALL_HISTORY_DIALOG_CODE)
        dialog.show(fragmentManager, DELETE_ALL_HISTORY_TAG)
    }

    private fun showHistory(items: List<Result>) {
        val clickListener = { result: Result ->
            val intent: Intent = when (result) {
                is OneScaleResult ->
                    Intent(context, OneScaleResultActivity::class.java)
                            .putExtra(OneScaleResultActivity.RESULT_DATA_PARAM, result)
                is ThreeScalesResult -> Intent(context, ThreeScalesResultActivity::class.java)
                        .putExtra(ThreeScalesResultActivity.RESULT_DATA_PARAM, result)
                is ScaleResult -> Intent(context, OstResultActivity::class.java)
                        .putExtra(OstResultActivity.RESULT_DATA_PARAM, result)
                else -> {
                    throw IllegalArgumentException("Unexpected result object")
                }
            }
            startActivity(intent)
        }
        val onEmptyHistoryListener = { isEmpty: Boolean ->
            if (isEmpty) {
                setMenuVisibility(false)
                if (empty_history_view.isInLayout) {
                    empty_history_view.visibility = View.VISIBLE
                } else empty_history_view.inflate()
            }
        }

        history_recycler_view.adapter = HistoryItemsAdapter(items.asReversed().toMutableList(), clickListener, onEmptyHistoryListener)
        ItemTouchHelper(object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                dao.deleteFromHistory(viewHolder.adapterPosition)
                (history_recycler_view.adapter as HistoryItemsAdapter).deleteItem(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(history_recycler_view)

    }

    private fun showEmptyHistory(show: Boolean) {
        if (show) {
            empty_history_view.inflate()
        } else {
            empty_history_view.visibility = View.INVISIBLE
        }

    }


}
