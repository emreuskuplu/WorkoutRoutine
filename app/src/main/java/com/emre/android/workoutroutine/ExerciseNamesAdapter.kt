package com.emre.android.workoutroutine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WorkoutAdapter(val exerciseNameList: List<String>) : RecyclerView.Adapter<WorkoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview_exercise, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.exerciseName.text = exerciseNameList[position]
    }

    override fun getItemCount(): Int {
        return exerciseNameList.size
    }
}