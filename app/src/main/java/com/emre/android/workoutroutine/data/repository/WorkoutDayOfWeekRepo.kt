package com.emre.android.workoutroutine.data.repository

import com.emre.android.workoutroutine.data.AppDatabase
import com.emre.android.workoutroutine.data.model.WorkoutDayOfWeek

class WorkoutDayOfWeekRepo(appDatabase: AppDatabase) {
    private val workoutDayOfWeekDao = appDatabase.workoutDayOfWeekDao()

    fun insertToDb(workoutDayOfWeek: WorkoutDayOfWeek) {
        workoutDayOfWeekDao.insert(workoutDayOfWeek)
    }

    fun deleteAllMatchWorkoutIdInDb(workoutId: Long) {
        workoutDayOfWeekDao.delete(workoutId)
    }
}