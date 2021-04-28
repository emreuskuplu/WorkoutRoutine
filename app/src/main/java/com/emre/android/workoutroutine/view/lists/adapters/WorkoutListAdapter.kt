package com.emre.android.workoutroutine.view.lists.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.data.model.Exercise
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.data.model.Workout
import com.emre.android.workoutroutine.view.DeleteWorkoutDialog
import com.emre.android.workoutroutine.view.popupwindows.EditDeletePopupWindow
import com.emre.android.workoutroutine.view.lists.viewholders.WorkoutViewHolder

class WorkoutListAdapter(private val fragmentManager: FragmentManager) : RecyclerView.Adapter<WorkoutViewHolder>() {
    private var workoutList: MutableList<Pair<Workout, List<Exercise>>> = mutableListOf()

    fun setWorkoutList(workoutList: List<Pair<Workout, List<Exercise>>>) {
        this.workoutList = workoutList.toMutableList()
        notifyDataSetChanged()
    }

    fun removeWorkoutByPositionAndUpdateWorkoutList(position: Int) {
        workoutList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)
        val popupWindow = EditDeletePopupWindow(parent)

        return WorkoutViewHolder(view) { workoutMenu, position ->
            popupWindow.showAsDropDown(workoutMenu, 0, -36)

            popupWindow.edit.setOnClickListener {
                popupWindow.dismiss()
            }

            popupWindow.delete.setOnClickListener {
                val workoutId = workoutList[position].first.id
                val deleteWorkoutDialog = DeleteWorkoutDialog(position, workoutId!!)

                deleteWorkoutDialog.show(fragmentManager, "DeleteWorkout")
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