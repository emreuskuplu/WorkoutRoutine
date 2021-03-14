package com.emre.android.workoutroutine

import androidx.lifecycle.ViewModel
import java.util.*

class WorkoutViewModel(
        private val listWorkoutDay: MutableList<WorkoutDay> = mutableListOf()) : ViewModel() {

    fun getWorkoutDays(): MutableList<WorkoutDay> {
        val calendar = GregorianCalendar.getInstance()

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

        return  listWorkoutDay
    }
}