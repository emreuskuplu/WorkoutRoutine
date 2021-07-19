package com.emre.android.workoutroutine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val bottomBarLiveData = MutableLiveData<Boolean>()
    val onUserInteractionLiveData = MutableLiveData<Unit>()
}


