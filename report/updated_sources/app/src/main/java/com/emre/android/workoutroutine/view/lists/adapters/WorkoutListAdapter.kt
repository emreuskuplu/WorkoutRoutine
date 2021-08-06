package com.emre.android.workoutroutine.view.lists.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.data.model.Exercise
import com.emre.android.workoutroutine.data.model.Workout
import com.emre.android.workoutroutine.databinding.ItemWorkoutBinding
import com.emre.android.workoutroutine.view.DeleteWorkoutDialog
import com.emre.android.workoutroutine.view.popupwindows.EditDeletePopupWindow

class WorkoutListAdapter(
    private val fragmentManager: FragmentManager,
    private val viewModelStoreOwner: ViewModelStoreOwner
) : RecyclerView.Adapter<WorkoutListAdapter.WorkoutViewHolder>() {

    private var workoutList: MutableList<Pair<Workout, List<Exercise>>> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setWorkoutList(workoutList: List<Pair<Workout, List<Exercise>>>) {
        this.workoutList = workoutList.toMutableList()
        notifyDataSetChanged()
    }

    fun getWorkoutName(position: Int): String {
        return workoutList[position].first.workoutName
    }

    /**
     * If this method called after setWorkoutList called, then this method may remove wrong item or
     * it may throws IndexOutOfBoundsException.
     * So it uses the size condition.
     */
    fun removeWorkoutByPositionAndUpdateWorkoutList(position: Int, workoutListSize: Int) {
        if (workoutListSize == this.workoutList.size) {
            this.workoutList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemWorkoutBinding.inflate(layoutInflater, parent, false)
        val popupWindow = EditDeletePopupWindow(parent)

        return WorkoutViewHolder(binding) { workoutMenu, position ->
            popupWindow.let {
                it.showAsDropDown(workoutMenu, 0, -36)
                it.binding.edit.setOnClickListener {
                    popupWindow.dismiss()
                }
                it.binding.delete.setOnClickListener {
                    val workoutId = workoutList[position].first.id
                    val deleteWorkoutDialog =
                        DeleteWorkoutDialog(
                            position,
                            workoutId,
                            workoutList.size,
                            viewModelStoreOwner
                        )
                    deleteWorkoutDialog.show(fragmentManager, "DeleteWorkout")
                    popupWindow.dismiss()
                }
            }
        }
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val linearLayoutManagerVertical = LinearLayoutManager(
            holder.itemView.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        val exerciseNames = workoutList[position].second.map { it.exerciseName }
        val exerciseNamesAdapter = ExerciseNameListAdapter(exerciseNames)
        holder.binding.let {
            it.exerciseNamesRecyclerview.layoutManager = linearLayoutManagerVertical
            it.exerciseNamesRecyclerview.adapter = exerciseNamesAdapter
            it.workoutName.text = workoutList[position].first.workoutName
        }
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }

    class WorkoutViewHolder(
        val binding: ItemWorkoutBinding,
        onMenuItemClicked: (View, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.menuIB.setOnClickListener {
                onMenuItemClicked(it, adapterPosition)
            }
        }
    }
}
