package com.emre.android.workoutroutine.view.lists.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.R

class ExerciseNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val exerciseName: TextView = itemView.findViewById(R.id.exercise_name)
}