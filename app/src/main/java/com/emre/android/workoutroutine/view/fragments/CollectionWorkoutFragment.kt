package com.emre.android.workoutroutine.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.data.AppDatabase
import com.emre.android.workoutroutine.databinding.FragmentCollectionWorkoutBinding
import com.emre.android.workoutroutine.view.lists.DayListScrollListener
import com.emre.android.workoutroutine.view.lists.adapters.DayListAdapter
import com.emre.android.workoutroutine.viewmodel.CollectionWorkoutListViewModel
import com.emre.android.workoutroutine.viewmodel.CollectionWorkoutListViewModelFactory
import com.emre.android.workoutroutine.viewmodel.MainViewModel

/**
 * @property _binding This property is only valid between onCreateView and onDestroyView.
 * Binding instance is unnecessary to stay in memory between out this lifecycle methods.
 * @property binding Instead using null check while each time using _binding,
 * binding property has null check itself so it can be used when using view instances.
 */
class CollectionWorkoutFragment : Fragment() {

    private var _binding: FragmentCollectionWorkoutBinding? = null
    private val binding get() = _binding ?: throw Exception("Binding class is not found.")
    private lateinit var collectionWorkoutListViewModel: CollectionWorkoutListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentCollectionWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        val dayListAdapter = DayListAdapter()
        val dayListScroll = DayListScrollListener(linearLayoutManager, requireContext())
        val thirdVisibleDayObservable = dayListScroll.thirdVisibleDayObservable
        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.run {
            toolbarLiveData.value = true
            toolbarBackButtonLiveData.value = false
            bottomBarLiveData.value = true
        }
        collectionWorkoutListViewModel =
            ViewModelProvider(
                requireActivity(),
                CollectionWorkoutListViewModelFactory(
                    AppDatabase.getInstance(requireActivity().application)
                )
            ).get(CollectionWorkoutListViewModel::class.java)
        val dayList = collectionWorkoutListViewModel.getDays()
        val workoutCollectionAdapter = WorkoutCollectionAdapter(
            this.requireActivity(),
            dayList.size
        )
        val workoutPageChangeCallback = WorkoutPageChangeCallback(linearLayoutManager)
        dayListAdapter.setDays(dayList)
        collectionWorkoutListViewModel.let {
            it.subscribeThirdVisibleDayObservable(thirdVisibleDayObservable)
            it.monthLiveData.observe(this as LifecycleOwner) { event ->
                mainViewModel.toolbarTitleLiveData.value =
                    event.getContentIfNotHandled() ?: return@observe
            }
            it.dayListLiveData.observe(this as LifecycleOwner) { event ->
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
        binding.run {
            workoutPager.let {
                it.adapter = workoutCollectionAdapter
                it.registerOnPageChangeCallback(workoutPageChangeCallback)
                it.setCurrentItem(
                    collectionWorkoutListViewModel.viewPagerStartPosition,
                    false
                )
                it.offscreenPageLimit = 2

                Log.i(this.javaClass.simpleName, "${binding.workoutPager.currentItem}")
            }
            daysRecyclerview.let {
                it.layoutManager = linearLayoutManager
                it.adapter = dayListAdapter
                it.addOnScrollListener(dayListScroll)
            }
            newWorkoutButton.setOnClickListener {
                findNavController().navigate(R.id.action_workout_pager_to_fragment_add_exercise)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        collectionWorkoutListViewModel.viewPagerStartPosition = binding.workoutPager.currentItem
        _binding = null
        Log.i(this.javaClass.simpleName, "Destroyed")
    }
}