package com.emre.android.workoutroutine.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.emre.android.workoutroutine.data.model.Exercise

@Dao
interface ExerciseDao {

    @Insert
    fun insert(exercise: Exercise)

    @Query("SELECT * FROM exercise WHERE workoutId = :workoutId")
    fun getAllMatchWorkoutId(workoutId: Long): List<Exercise>

    @Query("DELETE FROM exercise WHERE workoutId = :workoutId")
    fun deleteAllMatchWorkoutId(workoutId: Long)
}
