package com.emre.android.workoutroutine.view.lists.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.data.model.Day
import com.emre.android.workoutroutine.databinding.ItemDayBinding

class DaysAdapter: RecyclerView.Adapter<DaysAdapter.DayViewHolder>() {

    private var dayList = listOf<Day>()

    fun setDays(dayList: List<Day>) {
        this.dayList = dayList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDayBinding.inflate(layoutInflater, parent, false)

        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.binding.workoutDayNumber.text = dayList[position].dayNumber
        holder.binding.workoutDayNumber.setTextColor(
            ContextCompat.getColor(holder.itemView.context, R.color.colorFirstAndFifthDay)
        )
        holder.binding.workoutDayNumber.textSize = 20F

        holder.binding.workoutDayName.text = dayList[position].dayName
        holder.binding.workoutDayName.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    class DayViewHolder(val binding: ItemDayBinding): RecyclerView.ViewHolder(binding.root)
}