package com.emre.android.workoutroutine.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.view.lists.DayListScrollListener
import com.emre.android.workoutroutine.view.lists.adapters.DayListAdapter
import com.emre.android.workoutroutine.viewmodel.CollectionWorkoutViewModel
import com.emre.android.workoutroutine.viewmodel.CollectionWorkoutViewModelFactory

class CollectionWorkoutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collection_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPagerStartPosition = 18
        val linearLayoutManager = LinearLayoutManager(context, HORIZONTAL, false)

        val monthTextView: TextView = view.findViewById(R.id.month)

        val dayListAdapter = DayListAdapter()
        val dayListScroll = DayListScrollListener(linearLayoutManager, context)
        val dayListRecyclerView: RecyclerView = view.findViewById(R.id.workout_days_recyclerview)

        val thirdVisibleDayObservable = dayListScroll.thirdVisibleDayObservable
        val collectionWorkoutViewModel =
                ViewModelProvider(this,
                        CollectionWorkoutViewModelFactory(thirdVisibleDayObservable))
                        .get(CollectionWorkoutViewModel::class.java)

        val dayList = collectionWorkoutViewModel.getDays()

        val workoutCollectionAdapter = WorkoutCollectionAdapter(this, dayList.size)
        val workoutPageChangeCallback = WorkoutPageChangeCallback(linearLayoutManager)
        val viewPager: ViewPager2 = view.findViewById(R.id.workout_pager)

        collectionWorkoutViewModel.monthLiveData.observe(this as LifecycleOwner) {
            monthTextView.text = it
        }

        dayListAdapter.setDays(dayList)
        dayListRecyclerView.layoutManager = linearLayoutManager
        dayListRecyclerView.adapter = dayListAdapter
        dayListRecyclerView.addOnScrollListener(dayListScroll)

        viewPager.adapter = workoutCollectionAdapter
        viewPager.registerOnPageChangeCallback(workoutPageChangeCallback)
        viewPager.setCurrentItem(viewPagerStartPosition, false)

        collectionWorkoutViewModel.dayListLiveData.observe(this as LifecycleOwner) {
            dayListAdapter.setDays(it.second)
            dayListAdapter.notifyDataSetChanged()
            workoutCollectionAdapter.setItemCountSize(it.second.size)

            if (it.first > 23) { // It checks the day list position for adding future days.
                dayListRecyclerView.smoothScrollToPosition(it.first) // Because of notifyDataSetChanged is called. It triggers the scroll listener to update the visible item positions.
            } else {
                // After adapter data is changed with past days. The position is changed because of head of day list is added with new items. Position must be come back to previous place.
                viewPager.setCurrentItem(it.first, false)
            }
        }
    }
}