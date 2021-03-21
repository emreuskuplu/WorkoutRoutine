package com.emre.android.workoutroutine.view.lists.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.view.popupwindows.EditDeletePopupWindow
import com.emre.android.workoutroutine.view.lists.viewholders.WorkoutViewHolder

class WorkoutListAdapter(private var workoutList: List<Pair<String, List<String>>>) : RecyclerView.Adapter<WorkoutViewHolder>() {

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
        val exerciseNamesAdapter = ExerciseNameListAdapter(workoutList[position].second)

        holder.exerciseNamesRecyclerView.layoutManager = linearLayoutManagerVertical
        holder.exerciseNamesRecyclerView.adapter = exerciseNamesAdapter

        holder.workoutName.text = workoutList[position].first
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }
}