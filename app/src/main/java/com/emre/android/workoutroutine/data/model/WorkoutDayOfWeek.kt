package com.emre.android.workoutroutine.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WorkoutDayOfWeek(
    var workoutId: Long,
    var dayOfWeek: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
