package com.emre.android.workoutroutine.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
    var workoutId: Long? = null,
    var exerciseName: String,
    var time: String? = null,
    var exerciseType: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
