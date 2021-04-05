package com.emre.android.workoutroutine.view.fragments

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class WorkoutCollectionAdapter(viewPagerFragment: Fragment, private var dayListSize: Int): FragmentStateAdapter(viewPagerFragment) {

    override fun getItemCount(): Int {
        return dayListSize
    }

    override fun createFragment(position: Int): Fragment {
        return WorkoutFragment()
    }

    fun setItemCountSize(dayListSize: Int) {
        this.dayListSize = dayListSize
    }
}