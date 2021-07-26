package com.emre.android.workoutroutine.view.popupwindows

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.databinding.WindowMainBinding

class MainPopupWindow(parent: ViewGroup) : PopupWindow(parent.context) {
    private val layoutInflater = LayoutInflater.from(parent.context)
    val binding = WindowMainBinding.inflate(layoutInflater)

    init {
        contentView = binding.root
        width = parent.resources.getDimensionPixelSize(R.dimen.window_width_five_items)
        height = parent.resources.getDimensionPixelSize(R.dimen.window_height_five_items)
        isOutsideTouchable = true
        isFocusable = true
    }
}