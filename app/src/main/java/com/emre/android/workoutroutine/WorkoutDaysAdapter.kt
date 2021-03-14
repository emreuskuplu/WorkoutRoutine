package com.emre.android.workoutroutine

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WorkoutDaysAdapter(
        private val listWorkoutDay: MutableList<WorkoutDay>,
        private val view: View) : RecyclerView.Adapter<WorkoutDaysViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutDaysViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout_day, parent, false)

        return WorkoutDaysViewHolder(view) {
            for (i in 0 until listWorkoutDay.size) {
                if (listWorkoutDay[i].isSelectedWorkoutDay) {
                    listWorkoutDay[i].isSelectedWorkoutDay = false
                }
            }
            listWorkoutDay[it].isSelectedWorkoutDay = true
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: WorkoutDaysViewHolder, position: Int) {
        holder.workoutDayNumberTextView.text = listWorkoutDay[position].workoutDayNumber
        holder.workoutDayNameTextView.text = listWorkoutDay[position].workoutDayName

        if (listWorkoutDay[position].isSelectedWorkoutDay) {
            holder.workoutDayNameTextView.visibility = View.VISIBLE
            holder.workoutDayNumberTextView.setTextColor(view.resources.getColor(R.color.colorSecondary))
            holder.workoutDayNumberTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25F)
        } else {
            holder.workoutDayNameTextView.visibility = View.INVISIBLE
            holder.workoutDayNumberTextView.setTextColor(view.resources.getColor(R.color.white))
            holder.workoutDayNumberTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        }
    }

    override fun getItemCount(): Int {
        return listWorkoutDay.size
    }
}