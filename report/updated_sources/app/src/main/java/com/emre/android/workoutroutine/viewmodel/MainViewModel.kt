package com.emre.android.workoutroutine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val toolbarLiveData = MutableLiveData<Boolean>()
    val toolbarBackButtonLiveData = MutableLiveData<Boolean>()
    val toolbarTitleLiveData = MutableLiveData<String>()
    val bottomBarLiveData = MutableLiveData<Boolean>()
    val onUserInteractionLiveData = MutableLiveData<Unit>()
}
