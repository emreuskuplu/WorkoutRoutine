package com.emre.android.workoutroutine.view.lists.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.data.model.Day
import com.emre.android.workoutroutine.databinding.ItemDayBinding

class DayListAdapter : RecyclerView.Adapter<DayListAdapter.DayViewHolder>() {

    private var dayList = listOf<Day>()

    @SuppressLint("NotifyDataSetChanged")
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
        holder.binding.run {
            workoutDayNumber.let {
                it.text = dayList[position].dayNumber
                it.setTextColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.colorFirstAndFifthDay)
                )
                it.textSize = 20F
            }
            workoutDayName.let {
                it.text = dayList[position].dayName
                it.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    class DayViewHolder(val binding: ItemDayBinding) : RecyclerView.ViewHolder(binding.root)
}
