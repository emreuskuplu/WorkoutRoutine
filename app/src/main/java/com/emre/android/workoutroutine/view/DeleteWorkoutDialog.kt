package com.emre.android.workoutroutine.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.databinding.DialogDeleteWorkoutBinding
import com.emre.android.workoutroutine.viewmodel.WorkoutListViewModel
import com.jakewharton.rxbinding4.view.clicks

/**
 * @param positionForRemoveWorkoutInList It is used for remove list item after workouts in database is updated.
 * RecyclerView's remove animation will broken if list item is removed before database updates.
 * Because of notifyDataSetChanged method will be called after database changes.
 */
class DeleteWorkoutDialog(
    private val positionForRemoveWorkoutInList: Int,
    private val workoutId: Long,
    private val workoutListSizeForConditionToRemoveItemFromAdapter: Int,
    private val viewModelStoreOwner: ViewModelStoreOwner
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity(), R.style.DialogTheme)
        val binding = DialogDeleteWorkoutBinding.inflate(layoutInflater)
        val workoutsViewModel =
            ViewModelProvider(viewModelStoreOwner).get(WorkoutListViewModel::class.java)
        builder.setView(binding.root)
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        workoutsViewModel
            .subscribeDeleteWorkoutWithExercisesInside(binding.deleteButton.clicks().map {
                dismiss()
                (positionForRemoveWorkoutInList to workoutId) to workoutListSizeForConditionToRemoveItemFromAdapter
            })
        return builder.create()
    }
}