package com.emre.android.workoutroutine

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val exerciseName: TextView = itemView.findViewById(R.id.exercise_name)
}