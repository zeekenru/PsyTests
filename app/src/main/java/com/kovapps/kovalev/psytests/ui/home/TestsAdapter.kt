package com.kovapps.kovalev.psytests.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.enities.BaseTest


class TestsAdapter(
    private val data: List<BaseTest>,
    private val clickListener: (BaseTest) -> Unit
) : RecyclerView.Adapter<TestsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(root: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(root.context).inflate(R.layout.test_item, root, false)
        return ViewHolder(v, clickListener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(
        private val view: View,
        private val clickListener: (BaseTest) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val testName: TextView = view.findViewById(R.id.new_item_test_name_text)
        private val qsCount: Chip = view.findViewById(R.id.question_count_chip)
        private val durationText: Chip = view.findViewById(R.id.duration_chip)

        fun bind(test: BaseTest) {
            testName.text = test.name
            if (test.questionsCount != null) qsCount.text = test.questionsCount.toString()
            else qsCount.visibility = View.GONE
            durationText.text = test.duration
            testName.text = test.name.replaceFirstChar { it.uppercase() }

            view.setOnClickListener { clickListener(test) }
        }
    }


}