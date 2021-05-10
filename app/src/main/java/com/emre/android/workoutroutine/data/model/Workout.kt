package com.emre.android.workoutroutine.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workout(
        var workoutName: String = "",
        var workoutDateStart: String,
        var workoutDateEnd: String? = null,
        var workoutReminder: String? = null,
        var mon: Boolean = false,
        var tue: Boolean = false,
        var wed: Boolean = false,
        var thu: Boolean = false,
        var fri: Boolean = false,
        var sat: Boolean = false,
        var sun: Boolean = false
) {
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0
}