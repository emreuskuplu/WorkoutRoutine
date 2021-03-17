package com.emre.android.workoutroutine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WorkoutsAdapter(private var workoutList: List<Pair<String, List<String>>>) : RecyclerView.Adapter<WorkoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)

        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val linearLayoutManagerVertical =
                LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
        val exerciseNamesAdapter = ExerciseNamesAdapter(workoutList[position].second)

        holder.exerciseNamesRecyclerView.layoutManager = linearLayoutManagerVertical
        holder.exerciseNamesRecyclerView.adapter = exerciseNamesAdapter

        holder.workoutName.text = workoutList[position].first
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }
}