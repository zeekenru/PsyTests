package com.kovapps.kovalev.psytests.ui.home

import android.animation.ObjectAnimator
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.enities.Test
import kotlinx.android.synthetic.main.test_list_item.view.*


class TestsAdapter(private val data: List<Test>, private val passClickListener: (Test) -> Unit,
                   private val moreInfoClickListener: (Test) -> Unit, private val isTablet: Boolean) : RecyclerView.Adapter<TestsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(root: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(root.context).inflate(R.layout.test_list_item, root, false)
        return ViewHolder(v, passClickListener, moreInfoClickListener, isTablet)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(private val view: View,
                     private val clickListener: (Test) -> Unit,
                     private val moreInfoClickListener: (Test) -> Unit, private val isTablet: Boolean)
        : RecyclerView.ViewHolder(view) {
        fun bind(test: Test) {
            if (isTablet) {
                view.description_text.text = test.description
                view.more_info_btn.setOnClickListener {
                    ObjectAnimator.ofInt(view.description_text, "maxLines", 100)
                            .setDuration(200).start()
                }
            } else {
                view.more_info_btn.setOnClickListener { moreInfoClickListener(test) }
            }
            if (test.description.isNullOrEmpty()){
                view.more_info_btn.visibility = View.INVISIBLE
            }
            view.test_name.text = test.name
            if (test.questionsCount != null) view.questions_count.text =
                    "${test.questionsCount} ${getEnumEnd(test.questionsCount)}"
            else view.questions_count.visibility = View.GONE
            view.duration_text.text = test.duration
            view.pass_btn.setOnClickListener { clickListener(test) }

        }


        private fun getEnumEnd(count: Int): String {
            if (count in 11..19) {
                return "вопросов"
            }
            return when (count % 10) {
                1 -> "вопрос"
                2, 3, 4 -> "вопроса"
                else -> "вопросов"
            }
        }
    }


}