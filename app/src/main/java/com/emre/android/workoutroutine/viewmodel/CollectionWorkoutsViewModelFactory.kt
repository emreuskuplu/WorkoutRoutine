package com.emre.android.workoutroutine.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emre.android.workoutroutine.data.AppDatabase

@Suppress("UNCHECKED_CAST")
class CollectionWorkoutsViewModelFactory(
    private val appDatabase: AppDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectionWorkoutsViewModel::class.java)) {
            return CollectionWorkoutsViewModel(appDatabase) as T
        }
        throw IllegalArgumentException("Unknown CollectionWorkoutsViewModel")
    }
}