package com.emre.android.workoutroutine.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import com.emre.android.workoutroutine.R
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable

class MainPopupWindow(parent: ViewGroup): PopupWindow(parent.context) {
    private val popupWindowView = LayoutInflater.from(parent.context).inflate(R.layout.window_main, parent, false)

    private val workouts: TextView = popupWindowView.findViewById(R.id.workouts)
    private val archive: TextView = popupWindowView.findViewById(R.id.archive)
    private val importExport: TextView = popupWindowView.findViewById(R.id.import_export)
    private val settings: TextView = popupWindowView.findViewById(R.id.settings)
    private val help: TextView = popupWindowView.findViewById(R.id.help)

    init {
        contentView = popupWindowView
        width = parent.resources.getDimensionPixelSize(R.dimen.window_width_five_items)
        height = parent.resources.getDimensionPixelSize(R.dimen.window_height_five_items)
        isOutsideTouchable = true
        isFocusable = true
    }

    fun getWorkoutsClicksObservable(): Observable<Unit> {
        return workouts.clicks()
    }

    fun getArchiveClicksObservable(): Observable<Unit> {
        return archive.clicks()
    }

    fun getImportExportClicksObservable(): Observable<Unit> {
        return importExport.clicks()
    }

    fun getSettingsClicksObservable(): Observable<Unit> {
        return settings.clicks()
    }

    fun getHelpClicksObservable(): Observable<Unit> {
        return help.clicks()
    }
}