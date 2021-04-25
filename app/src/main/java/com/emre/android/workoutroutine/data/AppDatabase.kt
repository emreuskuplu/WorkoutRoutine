package com.emre.android.workoutroutine.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emre.android.workoutroutine.data.model.Exercise
import com.emre.android.workoutroutine.data.model.Set
import com.emre.android.workoutroutine.data.model.Workout

@Database(entities = [
    Workout::class,
    Exercise::class,
    Set::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        private var appDatabase: AppDatabase? = null

        fun getInstance(applicationContext: Context): AppDatabase {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(applicationContext,
                    AppDatabase::class.java, "app-database").build()
            }

            return appDatabase!!
        }
    }
}