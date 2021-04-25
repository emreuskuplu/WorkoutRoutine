package com.emre.android.workoutroutine.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workout(
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null,
        var workoutName: String = "",
        var workoutDateStart: String,
        var workoutDateEnd: String? = null,
        var workoutReminder: String? = null,
        var monday: Boolean = false,
        var tuesday: Boolean = false,
        var wednesday: Boolean = false,
        var thursday: Boolean = false,
        var friday: Boolean = false,
        var saturday: Boolean = false,
        var sunday: Boolean = false)