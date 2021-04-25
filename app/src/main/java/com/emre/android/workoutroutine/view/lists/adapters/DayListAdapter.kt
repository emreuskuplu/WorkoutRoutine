package com.emre.android.workoutroutine.view.lists.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.data.model.Day
import com.emre.android.workoutroutine.view.lists.viewholders.DayViewHolder

class DayListAdapter: RecyclerView.Adapter<DayViewHolder>() {

    private var dayList = listOf<Day>()

    fun setDays(dayList: List<Day>) {
        this.dayList = dayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)

        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
            holder.workoutDayNumberTextView.text = dayList[position].dayNumber
            holder.workoutDayNumberTextView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorFirstAndFifthDay))
            holder.workoutDayNumberTextView.textSize = 20F

            holder.workoutDayNameTextView.text = dayList[position].dayName
            holder.workoutDayNameTextView.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int {
        return dayList.size
    }
}