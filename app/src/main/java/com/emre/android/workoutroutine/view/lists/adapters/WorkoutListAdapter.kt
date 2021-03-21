package com.emre.android.workoutroutine.view.lists.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.view.lists.viewholders.WorkoutViewHolder

class WorkoutListAdapter(private var workoutList: List<Pair<String, List<String>>>) : RecyclerView.Adapter<WorkoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)

        return WorkoutViewHolder(view) {
            popupWindow(parent).showAsDropDown(it, -260, -36)
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

    private fun popupWindow(parent: ViewGroup): PopupWindow {
        val popupWindowView = LayoutInflater.from(parent.context).inflate(R.layout.card_popup_window, parent, false)
        val edit: TextView = popupWindowView.findViewById(R.id.edit)
        val delete: TextView = popupWindowView.findViewById(R.id.delete)
        val popupWindow = PopupWindow(parent.context)

        popupWindow.contentView = popupWindowView
        popupWindow.width = parent.resources.getDimensionPixelSize(R.dimen.card_popup_menu_width)
        popupWindow.height = parent.resources.getDimensionPixelSize(R.dimen.card_popup_menu_height)
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        edit.setOnClickListener {
            popupWindow.dismiss()
        }
        delete.setOnClickListener {
            popupWindow.dismiss()
        }

        return popupWindow
    }
}