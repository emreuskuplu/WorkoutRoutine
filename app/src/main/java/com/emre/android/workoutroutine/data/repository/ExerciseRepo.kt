package com.emre.android.workoutroutine.data.repository

import com.emre.android.workoutroutine.data.AppDatabase
import com.emre.android.workoutroutine.data.model.Exercise

class ExerciseRepo(appDatabase: AppDatabase) {

    private val exerciseDao = appDatabase.exerciseDao()

    // TODO: This method will be used when the adding exercise page is added
    fun insertToDb(exercise: Exercise) {
        exerciseDao.insert(exercise)
    }

    fun getAllMatchWorkoutIdInDb(workoutId: Long): List<Exercise> {
        return exerciseDao.getAllMatchWorkoutId(workoutId)
    }

    fun deleteAllMatchWorkoutIdInDb(workoutId: Long) {
        exerciseDao.deleteAllMatchWorkoutId(workoutId)
    }
}