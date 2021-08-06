package com.emre.android.workoutroutine.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emre.android.workoutroutine.data.dao.ExerciseDao
import com.emre.android.workoutroutine.data.dao.WorkoutDao
import com.emre.android.workoutroutine.data.dao.WorkoutDayOfWeekDao
import com.emre.android.workoutroutine.data.model.Exercise
import com.emre.android.workoutroutine.data.model.Set
import com.emre.android.workoutroutine.data.model.Workout
import com.emre.android.workoutroutine.data.model.WorkoutDayOfWeek

/**
 * https://developer.android.com/codelabs/kotlin-android-training-room-database#5
 */
@Database(
    entities = [
        Workout::class,
        WorkoutDayOfWeek::class,
        Exercise::class,
        Set::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutDayOfWeekDao(): WorkoutDayOfWeekDao
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(applicationContext: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room
                        .databaseBuilder(
                            applicationContext,
                            AppDatabase::class.java,
                            "app_database"
                        )
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
