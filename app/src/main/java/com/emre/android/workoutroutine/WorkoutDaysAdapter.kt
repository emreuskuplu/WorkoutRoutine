package com.emre.android.workoutroutine

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class WorkoutDaysAdapter(
        private val listWorkoutDay: List<WorkoutDay>) : RecyclerView.Adapter<WorkoutDaysViewHolder>() {

    private val workoutDayClickSubject = PublishSubject.create<Pair<List<WorkoutDay>, Int>>()
    val workoutDayClickStream: Observable<Pair<List<WorkoutDay>, Int>> = workoutDayClickSubject.hide()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutDaysViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout_day, parent, false)

        return WorkoutDaysViewHolder(view) {
            workoutDayClickSubject.onNext(listWorkoutDay to it)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: WorkoutDaysViewHolder, position: Int) {
        holder.workoutDayNumberTextView.text = listWorkoutDay[position].workoutDayNumber
        holder.workoutDayNameTextView.text = listWorkoutDay[position].workoutDayName

        if (listWorkoutDay[position].isSelectedWorkoutDay) {
            changeThemeForSelectedWorkoutDay(holder)
        } else {
            changeThemeForUnSelectedWorkoutDay(holder)
        }
    }

    override fun getItemCount(): Int {
        return listWorkoutDay.size
    }

    private fun changeThemeForSelectedWorkoutDay(holder: WorkoutDaysViewHolder) {
        val selectedWorkoutDayColor = ContextCompat.getColor(holder.itemView.context, R.color.colorSecondary)

        holder.workoutDayNameTextView.visibility = View.VISIBLE
        holder.workoutDayNumberTextView.setTextColor(selectedWorkoutDayColor)
        holder.workoutDayNumberTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25F)
    }

    private fun changeThemeForUnSelectedWorkoutDay(holder: WorkoutDaysViewHolder) {
        val unSelectedWorkoutDayColor = ContextCompat.getColor(holder.itemView.context, R.color.white)

        holder.workoutDayNameTextView.visibility = View.INVISIBLE
        holder.workoutDayNumberTextView.setTextColor(unSelectedWorkoutDayColor)
        holder.workoutDayNumberTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
    }
}