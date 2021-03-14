package com.emre.android.workoutroutine

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.*

class WorkoutViewModel(
    private val listWorkoutDay: MutableList<WorkoutDay> = mutableListOf()) : ViewModel() {

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun subscribeWorkoutDayClicks(workoutDayClicks: Observable<Pair<List<WorkoutDay>, Int>>) {
        workoutDayClicks.subscribe {
            val listWorkoutDayFromAdapter = it.first
            val adapterPosition = it.second

            for (i in listWorkoutDayFromAdapter.indices) {
                if (listWorkoutDayFromAdapter[i].isSelectedWorkoutDay) {
                    listWorkoutDayFromAdapter[i].isSelectedWorkoutDay = false
                }
            }

            listWorkoutDayFromAdapter[adapterPosition].isSelectedWorkoutDay = true
        }.addTo(disposables)
    }

    fun getWorkoutDays(): List<WorkoutDay> {
        val calendar = Calendar.getInstance()

        for (i in 0 until 31) {
            val dayNumber = calendar.get(Calendar.DAY_OF_MONTH).toString()
            val dayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
            val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

            listWorkoutDay.add(i, WorkoutDay(
                    dayNumber,
                    dayName ?: "",
                    monthName ?: ""))

            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        listWorkoutDay[0].isSelectedWorkoutDay = true
        return listWorkoutDay.toList()
    }

}