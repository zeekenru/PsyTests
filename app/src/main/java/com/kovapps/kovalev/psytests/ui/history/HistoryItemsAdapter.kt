package com.kovapps.kovalev.psytests.ui.history

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.enities.Result
import kotlinx.android.synthetic.main.history_item.view.*
import java.util.*


class HistoryItemsAdapter(private val data: MutableList<Result>,
                          private val onClickListener : (Result) -> Unit,
                          private val onEmptyHistoryListener : (Boolean) -> Unit)
    : RecyclerView.Adapter<HistoryItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return ViewHolder(view, onClickListener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])


    fun deleteItem(index : Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
        if (data.isEmpty()){ onEmptyHistoryListener(true) }
    }

    fun deleteAll() {
        val size = data.size
        data.clear()
        notifyItemRangeRemoved(0, size)
        onEmptyHistoryListener(true)
    }

    class ViewHolder(private val v : View, private val onClickListener: (Result) -> Unit) : RecyclerView.ViewHolder(v){
        fun bind(item : Result){
            v.test_name.text = item.name
            v.save_date.text = DateUtils.getRelativeTimeSpanString(item.time, Date().time, DateUtils.MINUTE_IN_MILLIS )
            v.setOnClickListener { onClickListener(item) }
        }
    }
}