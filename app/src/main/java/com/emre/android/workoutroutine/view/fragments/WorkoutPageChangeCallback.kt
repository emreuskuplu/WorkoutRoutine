package com.emre.android.workoutroutine.view.fragments

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2

class WorkoutPageChangeCallback(private val linearLayoutManager: LinearLayoutManager): ViewPager2.OnPageChangeCallback() {

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        linearLayoutManager.scrollToPositionWithOffset(position, (positionOffsetPixels * -0.1557).toInt())
    }
}