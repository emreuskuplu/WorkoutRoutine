package com.emre.android.workoutroutine.view.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @param viewPagerFragmentActivity FragmentActivity must be attach to FragmentStateAdapter to
 * track activity lifecycle instead of fragment lifecycle, because of fragments in
 * FragmentStateAdapter is not destroyed when fragment destroyed.
 */
class WorkoutCollectionAdapter(viewPagerFragmentActivity: FragmentActivity, private var dayListSize: Int) :
    FragmentStateAdapter(viewPagerFragmentActivity) {

    override fun getItemCount(): Int {
        return dayListSize
    }

    override fun createFragment(position: Int): Fragment {
        return WorkoutsFragment.newInstance(position + 2)
    }

    fun setItemCountSize(dayListSize: Int) {
        this.dayListSize = dayListSize
    }
}