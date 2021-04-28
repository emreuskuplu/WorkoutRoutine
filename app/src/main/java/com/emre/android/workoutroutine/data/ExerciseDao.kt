package com.emre.android.workoutroutine.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emre.android.workoutroutine.data.model.Exercise
import io.reactivex.rxjava3.core.Observable

@Dao
interface ExerciseDao {

    @Insert
    fun insertExercise(exercise: Exercise)

    @Query("SELECT * FROM exercise WHERE workoutId = :id")
    fun getExercises(id: Long): List<Exercise>

    @Update
    fun updateExercise(exercise: Exercise)

    @Query("DELETE FROM exercise WHERE id = :id")
    fun deleteExercise(id: Long)

    @Query("DELETE FROM exercise WHERE workoutId = :id")
    fun deleteExercises(id: Long)
}