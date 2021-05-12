package com.emre.android.workoutroutine.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workout(
        var workoutName: String = "",
        var workoutDateStart: String,
        var workoutDateEnd: String? = null,
        var workoutReminder: String? = null,
) {
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0
}