package com.emre.android.workoutroutine.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Set(
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null,
        var exerciseId: Long,
        var reps: Int,
        var kg: Int?,
        var rest: String?)