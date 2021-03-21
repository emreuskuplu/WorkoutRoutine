package com.emre.android.workoutroutine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable

class MainViewModel(menuWorkoutsClicks: Observable<Unit>,
                    menuArchiveClicks: Observable<Unit>,
                    menuImportExportClicks: Observable<Unit>,
                    menuSettingsClicks: Observable<Unit>,
                    menuHelpClicks: Observable<Unit>): ViewModel() {

    val menuWorkoutLiveData = MutableLiveData<Unit>()
    val menuArchiveLiveData = MutableLiveData<Unit>()
    val menuImportExportLiveData = MutableLiveData<Unit>()
    val menuSettingsLiveData = MutableLiveData<Unit>()
    val menuHelpLiveData = MutableLiveData<Unit>()

    init {
        menuWorkoutsClicks.subscribe(menuWorkoutLiveData::postValue)
        menuArchiveClicks.subscribe(menuArchiveLiveData::postValue)
        menuImportExportClicks.subscribe(menuImportExportLiveData::postValue)
        menuSettingsClicks.subscribe(menuSettingsLiveData::postValue)
        menuHelpClicks.subscribe(menuHelpLiveData::postValue)
    }
}