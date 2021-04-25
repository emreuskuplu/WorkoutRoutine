package com.emre.android.workoutroutine.view.lists.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.data.model.Exercise
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.data.model.Workout
import com.emre.android.workoutroutine.view.popupwindows.EditDeletePopupWindow
import com.emre.android.workoutroutine.view.lists.viewholders.WorkoutViewHolder

class WorkoutListAdapter(private var workoutList: List<Pair<Workout, List<Exercise>>>) : RecyclerView.Adapter<WorkoutViewHolder>() {

    fun setWorkoutList(workoutList: List<Pair<Workout, List<Exercise>>>) {
        this.workoutList = workoutList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)
        val popupWindow = EditDeletePopupWindow(parent)

        return WorkoutViewHolder(view) {
            popupWindow.showAsDropDown(it, 0, -36)

            popupWindow.edit.setOnClickListener {
                popupWindow.dismiss()
            }

            popupWindow.delete.setOnClickListener {
                popupWindow.dismiss()
            }
        }
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val linearLayoutManagerVertical =
                LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
        val exerciseNamesAdapter = ExerciseNameListAdapter(workoutList[position].second.map { it.exerciseName })

        holder.exerciseNamesRecyclerView.layoutManager = linearLayoutManagerVertical
        holder.exerciseNamesRecyclerView.adapter = exerciseNamesAdapter

        holder.workoutName.text = workoutList[position].first.workoutName
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }
}