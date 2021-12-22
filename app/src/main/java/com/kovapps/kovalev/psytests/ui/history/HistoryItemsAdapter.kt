package com.kovapps.kovalev.psytests.ui.history

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.enities.HistoryItem
import java.util.*


class HistoryItemsAdapter(
    private val data: MutableList<HistoryItem>,
    private val onClickListener: (HistoryItem) -> Unit,
    private val onEmptyHistoryListener: (Boolean) -> Unit
) : RecyclerView.Adapter<HistoryItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.test_item_history, parent, false)
        return ViewHolder(view, onClickListener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])


    fun deleteItem(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
        if (data.isEmpty()) {
            onEmptyHistoryListener(true)
        }
    }

    fun deleteAll() {
        val size = data.size
        data.clear()
        notifyItemRangeRemoved(0, size)
        onEmptyHistoryListener(true)
    }

    class ViewHolder(private val v: View, private val onClickListener: (HistoryItem) -> Unit) :
        RecyclerView.ViewHolder(v) {
        private val testName = v.findViewById<TextView>(R.id.test_name)
        private val saveDate = v.findViewById<TextView>(R.id.save_date)
        fun bind(item: HistoryItem) {
            testName.text = item.testName.replaceFirstChar { it.uppercase() }
            saveDate.text = DateUtils.getRelativeTimeSpanString(
                item.date,
                Date().time,
                DateUtils.MINUTE_IN_MILLIS
            )
            v.setOnClickListener { onClickListener(item) }
        }
    }
}