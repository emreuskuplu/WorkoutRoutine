package com.emre.android.workoutroutine.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.viewmodel.CollectionWorkoutViewModel
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

/**
 * @param positionForRemoveWorkoutInList It is used for remove list item after workouts in database is updated.
 * Recyclerview's remove animation will broken if list item is removed before database updates.
 * Because of notifyDataSetChanged method will be called after database changes.
 */
class DeleteWorkoutDialog(private val positionForRemoveWorkoutInList: Int, private val workoutId: Long) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val dialogView = layoutInflater.inflate(R.layout.dialog_delete_workout, null)
        val cancel: TextView = dialogView.findViewById(R.id.cancel)
        val delete: TextView = dialogView.findViewById(R.id.delete)
        val collectionWorkoutViewModel = ViewModelProvider(requireActivity()).get(CollectionWorkoutViewModel::class.java)

        builder.setView(dialogView)

        cancel.setOnClickListener {
            dismiss()
        }

        collectionWorkoutViewModel.subscribeToDeleteWorkoutWithExercisesInside(delete.clicks().map {
            dismiss()
            positionForRemoveWorkoutInList to workoutId
        })

        return builder.create()
    }
}