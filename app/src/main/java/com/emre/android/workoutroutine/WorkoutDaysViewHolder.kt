package com.emre.android.workoutroutine

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkoutDaysViewHolder(itemView: View,
                            onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

    val workoutDayNumberTextView: TextView = itemView.findViewById(R.id.workout_day)
    val workoutDayNameTextView: TextView = itemView.findViewById(R.id.workout_day_name)

    init {
        itemView.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }
}