package com.emre.android.workoutroutine.view.lists.viewholders

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.R

class WorkoutViewHolder(itemView: View, onWorkoutMenuClicked: (View, Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
    val workoutName: TextView = itemView.findViewById(R.id.workout_name)
    val exerciseNamesRecyclerView: RecyclerView = itemView.findViewById(R.id.exercise_names_recyclerview)
    private val workoutMenu: ImageButton = itemView.findViewById(R.id.workout_menu)

    init {
        workoutMenu.setOnClickListener {
            onWorkoutMenuClicked(it, adapterPosition)
        }
    }
}