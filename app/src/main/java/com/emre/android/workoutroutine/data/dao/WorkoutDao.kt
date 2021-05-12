package com.emre.android.workoutroutine.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.emre.android.workoutroutine.data.model.Workout
import io.reactivex.rxjava3.core.Observable

@Dao
interface WorkoutDao {

    @Insert
    fun insert(workout: Workout): Long

    @Query("SELECT workout.id, workoutName, workoutDateStart FROM workout INNER JOIN workoutdayofweek ON workout.id = workoutdayofweek.workoutId WHERE CASE WHEN workoutDateEnd IS NULL THEN workoutDateStart < :date ELSE :date BETWEEN workoutDateStart AND workoutDateEnd END AND dayOfWeek = :dayOfWeek")
    fun workoutsObservable(date: String, dayOfWeek: String): Observable<List<Workout>>

    @Query("DELETE FROM workout WHERE id = :id")
    fun delete(id: Long)
}