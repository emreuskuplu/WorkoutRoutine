package com.emre.android.workoutroutine.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.emre.android.workoutroutine.data.model.WorkoutDayOfWeek

@Dao
interface WorkoutDayOfWeekDao {

    @Insert
    fun insert(workoutDayOfWeek: WorkoutDayOfWeek)

    @Query("DELETE FROM workoutdayofweek WHERE workoutId = :workoutId")
    fun delete(workoutId: Long)
}