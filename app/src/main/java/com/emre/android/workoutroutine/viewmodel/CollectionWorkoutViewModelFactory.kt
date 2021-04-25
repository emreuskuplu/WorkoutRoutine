package com.emre.android.workoutroutine.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.core.Observable

@Suppress("UNCHECKED_CAST")
class CollectionWorkoutViewModelFactory(private val application: Application,
                                        private val thirdVisibleDayObservable: Observable<Int>,
                                        private val newWorkoutButtonObservable: Observable<Unit>): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectionWorkoutViewModel::class.java)) {
            return CollectionWorkoutViewModel(application,
                    thirdVisibleDayObservable,
                    newWorkoutButtonObservable) as T
        }
        throw IllegalArgumentException("Unknown CollectionWorkoutViewModel")
    }
}