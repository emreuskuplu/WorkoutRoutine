package com.emre.android.workoutroutine.data.repository

import com.emre.android.workoutroutine.data.AppDatabase
import com.emre.android.workoutroutine.data.model.Day
import com.emre.android.workoutroutine.data.model.Exercise
import com.emre.android.workoutroutine.data.model.Workout
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class WorkoutRepo(appDatabase: AppDatabase) {
    private val workoutDao = appDatabase.workoutDao()

    fun insertToDb(workout: Workout): Long {
        return workoutDao.insert(workout)
    }

    /**
     * Schedulers.single is necessary for synchronize working with other observables that in use
     * this single thread, because of all exercises must be added before this observable get exercises.
     */
    fun getWorkoutsWithExercisesInDbObservable(
        day: Day,
        exerciseRepo: ExerciseRepo
    ): Observable<List<Pair<Workout, List<Exercise>>>> {
        return workoutDao.workoutsObservable(day.date, day.dayName)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .map { workouts ->
                val workoutListWithExercises = mutableListOf<Pair<Workout, List<Exercise>>>()

                for (workout in workouts) {
                    workoutListWithExercises.add(workout to exerciseRepo.getAllMatchWorkoutIdInDb(workout.id))
                }

                workoutListWithExercises
            }
    }

    fun deleteInDb(id: Long) {
        workoutDao.delete(id)
    }
}