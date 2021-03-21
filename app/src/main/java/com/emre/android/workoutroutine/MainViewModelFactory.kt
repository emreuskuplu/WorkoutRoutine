package com.emre.android.workoutroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.core.Observable
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val menuWorkoutsClicks: Observable<Unit>,
                           private val menuArchiveClicks: Observable<Unit>,
                           private val menuImportExportClicks: Observable<Unit>,
                           private val menuSettingsClicks: Observable<Unit>,
                           private val menuHelpClicks: Observable<Unit>) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(menuWorkoutsClicks,
                    menuArchiveClicks,
                    menuImportExportClicks,
                    menuSettingsClicks,
                    menuHelpClicks) as T
        }
        throw IllegalArgumentException("Unknown WorkoutViewModel")
    }
}