package com.emre.android.workoutroutine.view.lists.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.R

class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val workoutDayNumberTextView: TextView = itemView.findViewById(R.id.workout_day_number)
    val workoutDayNameTextView: TextView = itemView.findViewById(R.id.workout_day_name)
}