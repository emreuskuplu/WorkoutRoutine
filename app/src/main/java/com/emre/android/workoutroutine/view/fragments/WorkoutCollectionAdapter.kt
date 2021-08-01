package com.emre.android.workoutroutine.view.fragments

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @param viewPagerFragmentActivity FragmentActivity must be attach to FragmentStateAdapter to
 * track activity lifecycle instead of fragment lifecycle, because of fragments in
 * FragmentStateAdapter is not destroyed when fragment destroyed.
 * dayListSize must be - 2 for preventing viewpager scrolls to beyond position + 2
 */
class WorkoutCollectionAdapter(
    viewPagerFragmentActivity: FragmentActivity,
    private var dayListSize: Int
) : FragmentStateAdapter(viewPagerFragmentActivity) {

    override fun getItemCount(): Int {
        return dayListSize - 2
    }

    override fun createFragment(position: Int): Fragment {
        return WorkoutListFragment.newInstance(position + 2)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItemCountSize(dayListSize: Int) {
        this.dayListSize = dayListSize - 2
        notifyDataSetChanged()
    }
}