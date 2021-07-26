package com.emre.android.workoutroutine.data.repository

import com.emre.android.workoutroutine.data.AppDatabase
import com.emre.android.workoutroutine.data.model.WorkoutDayOfWeek

class WorkoutDayOfWeekRepo(appDatabase: AppDatabase) {

    private val workoutDayOfWeekDao = appDatabase.workoutDayOfWeekDao()

    // TODO: This method will be used when the adding workout days page is added
    fun insertToDb(workoutDayOfWeek: WorkoutDayOfWeek) {
        workoutDayOfWeekDao.insert(workoutDayOfWeek)
    }

    fun deleteAllMatchWorkoutIdInDb(workoutId: Long) {
        workoutDayOfWeekDao.delete(workoutId)
    }
}