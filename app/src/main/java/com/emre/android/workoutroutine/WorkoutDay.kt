package com.emre.android.workoutroutine

data class WorkoutDay(
        var workoutDayNumber: String,
        var workoutDayName: String,
        var workoutMonthName: String,
        var isSelectedWorkoutDay: Boolean = false)