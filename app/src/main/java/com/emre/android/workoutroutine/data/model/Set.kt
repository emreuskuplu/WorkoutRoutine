package com.emre.android.workoutroutine.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Set(
    var exerciseId: Long,
    var reps: Int,
    var kg: Int?,
    var rest: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}