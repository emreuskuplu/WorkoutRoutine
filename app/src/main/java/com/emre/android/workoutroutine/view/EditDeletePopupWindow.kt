package com.emre.android.workoutroutine.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import com.emre.android.workoutroutine.R

class EditDeletePopupWindow(parent: ViewGroup): PopupWindow(parent.context) {
    private val popupWindowView = LayoutInflater.from(parent.context).inflate(R.layout.window_workout, parent, false)

    val edit: TextView
        get() = popupWindowView.findViewById(R.id.edit)
    val delete: TextView
        get() = popupWindowView.findViewById(R.id.delete)

    init {
        contentView = popupWindowView
        width = parent.resources.getDimensionPixelSize(R.dimen.window_width_two_items)
        height = parent.resources.getDimensionPixelSize(R.dimen.window_height_two_items)
        isOutsideTouchable = true
        isFocusable = true
    }
}
