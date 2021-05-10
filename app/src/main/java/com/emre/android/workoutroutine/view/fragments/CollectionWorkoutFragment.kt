package com.emre.android.workoutroutine.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.emre.android.workoutroutine.data.AppDatabase
import com.emre.android.workoutroutine.databinding.FragmentCollectionWorkoutBinding
import com.emre.android.workoutroutine.view.lists.DaysScrollListener
import com.emre.android.workoutroutine.view.lists.adapters.DaysAdapter
import com.emre.android.workoutroutine.viewmodel.CollectionWorkoutsViewModel
import com.emre.android.workoutroutine.viewmodel.CollectionWorkoutsViewModelFactory
import com.jakewharton.rxbinding4.view.clicks
import java.lang.Exception

/**
 * @property _binding This property is only valid between onCreateView and onDestroyView.
 * Binding instance is unnecessary to stay in memory between out this lifecycle methods.
 * @property binding Instead using null check while each time using _binding,
 * binding property has null check itself so it can be used when using view instances.
 */
class CollectionWorkoutFragment : Fragment() {

    private var _binding: FragmentCollectionWorkoutBinding? = null
    private val binding get() = _binding ?: throw Exception("Binding class is not found.")

    private lateinit var collectionWorkoutsViewModel: CollectionWorkoutsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentCollectionWorkoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(context, HORIZONTAL, false)

        val dayListAdapter = DaysAdapter()
        val dayListScroll = DaysScrollListener(linearLayoutManager, requireContext())

        val thirdVisibleDayObservable = dayListScroll.thirdVisibleDayObservable

        collectionWorkoutsViewModel =
            ViewModelProvider(
                requireActivity(),
                CollectionWorkoutsViewModelFactory(
                    AppDatabase.getInstance(requireActivity().application)
                )
            ).get(CollectionWorkoutsViewModel::class.java)

        collectionWorkoutsViewModel.subscribeThirdVisibleDayObservable(thirdVisibleDayObservable)
        collectionWorkoutsViewModel.subscribeNewWorkoutObservable(binding.newWorkoutButton.clicks())

        val dayList = collectionWorkoutsViewModel.getDays()

        val workoutCollectionAdapter = WorkoutCollectionAdapter(
            this.requireActivity(),
            dayList.size
        )
        val workoutPageChangeCallback = WorkoutPageChangeCallback(linearLayoutManager)

        collectionWorkoutsViewModel.monthLiveData.observe(this as LifecycleOwner) {
            binding.month.text = it
        }

        Log.i(this.javaClass.simpleName, "C ${collectionWorkoutsViewModel.viewPagerStartPosition}")

        binding.workoutPager.adapter = workoutCollectionAdapter
        binding.workoutPager.registerOnPageChangeCallback(workoutPageChangeCallback)
        binding.workoutPager.setCurrentItem(
            collectionWorkoutsViewModel.viewPagerStartPosition,
            false
        )

        Log.i(this.javaClass.simpleName, "${binding.workoutPager.currentItem}")

        dayListAdapter.setDays(dayList)
        binding.daysRecyclerview.layoutManager = linearLayoutManager
        binding.daysRecyclerview.adapter = dayListAdapter
        binding.daysRecyclerview.addOnScrollListener(dayListScroll)

        collectionWorkoutsViewModel.dayListLiveData.observe(this as LifecycleOwner) { event ->
            val (position, days) = event.getContentIfNotHandled() ?: return@observe

            dayListAdapter.setDays(days)
            workoutCollectionAdapter.setItemCountSize(days.size)

            /** It checks the day list position for adding future or past days.
             * The position is used differently in two conditions. */
            if (position > 23) {
                /** When notifyDataSetChanged is called, it breaks the design of visible days
                 * because of visible days design in scroll listener.
                 * So it triggers the scroll listener to same position for update the design of visible days. */
                binding.daysRecyclerview.smoothScrollToPosition(position)

            } else {
                /** After adapter data is changed with past days. The position is changed because of
                 * head of day list is added with new items. Position must be come back to previous place. */
                binding.workoutPager.setCurrentItem(position, false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        collectionWorkoutsViewModel.viewPagerStartPosition = binding.workoutPager.currentItem
        _binding = null

        Log.i(this.javaClass.simpleName, "Destroyed")
    }
}