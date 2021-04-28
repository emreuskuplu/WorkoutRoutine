package com.emre.android.workoutroutine.data

import android.content.Context
import com.emre.android.workoutroutine.data.model.Exercise
import com.emre.android.workoutroutine.data.model.Workout
import io.reactivex.rxjava3.core.Observable

class Repository(applicationContext: Context) {

    private var appDatabase = AppDatabase.getInstance(applicationContext)
    private var workoutDao = appDatabase.workoutDao()
    private var exerciseDao = appDatabase.exerciseDao()

    fun addWorkout(workout: Workout): Long {
        return workoutDao.insertWorkout(workout)
    }

    fun addExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise)
    }
    
    fun getExercisesInDb(id: Long): List<Exercise> {
        return exerciseDao.getExercises(id)
    }

    fun workoutsInDbObservable(): Observable<List<Workout>> {
        return workoutDao.workoutsObservable()
    }

    fun updateWorkout(workout: Workout) {
        workoutDao.updateWorkout(workout)
    }

    fun updateExercise(exercise: Exercise) {
        exerciseDao.updateExercise(exercise)
    }

    fun deleteWorkout(workoutId: Long) {
        workoutDao.deleteWorkout(workoutId)
    }

    fun deleteExercise(exerciseId: Long) {
        exerciseDao.deleteExercise(exerciseId)
    }

    fun deleteExercises(workoutId: Long) {
        exerciseDao.deleteExercises(workoutId)
    }
}