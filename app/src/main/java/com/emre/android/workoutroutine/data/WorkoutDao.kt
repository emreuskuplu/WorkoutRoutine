package com.emre.android.workoutroutine.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emre.android.workoutroutine.data.model.Workout
import io.reactivex.rxjava3.core.Observable

@Dao
interface WorkoutDao {

    @Insert
    fun insertWorkout(workout: Workout): Long

    @Query("SELECT * FROM workout")
    fun workoutsObservable(): Observable<List<Workout>>

    @Update
    fun updateWorkout(workout: Workout)

    @Query("DELETE FROM workout WHERE id = :id")
    fun deleteWorkout(id: Long)
}