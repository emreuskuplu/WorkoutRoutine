package com.emre.android.workoutroutine.view.lists.adapters

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.WorkoutDay
import com.emre.android.workoutroutine.view.lists.viewholders.WorkoutDayViewHolder
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class WorkoutDayListAdapter(
        private val workoutDayList: List<WorkoutDay>) : RecyclerView.Adapter<WorkoutDayViewHolder>() {

    private val workoutDayClickSubject = PublishSubject.create<Pair<List<WorkoutDay>, Int>>()
    val workoutDayClickObservable: Observable<Pair<List<WorkoutDay>, Int>> = workoutDayClickSubject.hide()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutDayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout_day, parent, false)

        return WorkoutDayViewHolder(view) {
            workoutDayClickSubject.onNext(workoutDayList to it)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: WorkoutDayViewHolder, position: Int) {
        holder.workoutDayNumberTextView.text = workoutDayList[position].workoutDayNumber
        holder.workoutDayNameTextView.text = workoutDayList[position].workoutDayName

        if (workoutDayList[position].isSelectedWorkoutDay) {
            changeThemeForSelectedWorkoutDay(holder)
        } else {
            changeThemeForUnSelectedWorkoutDay(holder)
        }
    }

    override fun getItemCount(): Int {
        return workoutDayList.size
    }

    private fun changeThemeForSelectedWorkoutDay(holder: WorkoutDayViewHolder) {
        val selectedWorkoutDayColor = ContextCompat.getColor(holder.itemView.context, R.color.colorSecondaryVariant)

        holder.workoutDayNameTextView.visibility = View.VISIBLE
        holder.workoutDayNumberTextView.setTextColor(selectedWorkoutDayColor)
        holder.workoutDayNumberTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25F)
    }

    private fun changeThemeForUnSelectedWorkoutDay(holder: WorkoutDayViewHolder) {
        val unSelectedWorkoutDayColor = ContextCompat.getColor(holder.itemView.context, R.color.white)

        holder.workoutDayNameTextView.visibility = View.INVISIBLE
        holder.workoutDayNumberTextView.setTextColor(unSelectedWorkoutDayColor)
        holder.workoutDayNumberTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
    }
}