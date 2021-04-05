package com.emre.android.workoutroutine.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.core.Observable

@Suppress("UNCHECKED_CAST")
class CollectionWorkoutViewModelFactory(private val thirdVisibleDayObservable: Observable<Int>): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectionWorkoutViewModel::class.java)) {
            return CollectionWorkoutViewModel(thirdVisibleDayObservable) as T
        }
        throw IllegalArgumentException("Unknown CollectionWorkoutViewModel")
    }
}