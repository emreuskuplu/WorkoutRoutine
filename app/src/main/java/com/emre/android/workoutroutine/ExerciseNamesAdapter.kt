package com.emre.android.workoutroutine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ExerciseNamesAdapter(private val exerciseNameList: List<String>) : RecyclerView.Adapter<ExerciseNameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseNameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise_name, parent, false)
        return ExerciseNameViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseNameViewHolder, position: Int) {
        holder.exerciseName.text = exerciseNameList[position]
    }

    override fun getItemCount(): Int {
        return exerciseNameList.size
    }
}