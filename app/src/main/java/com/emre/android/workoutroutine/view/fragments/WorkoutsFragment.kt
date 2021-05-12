package com.emre.android.workoutroutine.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.emre.android.workoutroutine.data.AppDatabase
import com.emre.android.workoutroutine.databinding.FragmentWorkoutBinding
import com.emre.android.workoutroutine.view.lists.adapters.WorkoutsAdapter
import com.emre.android.workoutroutine.viewmodel.CollectionWorkoutsViewModel
import com.emre.android.workoutroutine.viewmodel.WorkoutsViewModel
import com.emre.android.workoutroutine.viewmodel.WorkoutsViewModelFactory

/**
 * @property thirdVisibleDayPosition (viewpager position + 2) It is in use for getting day in dayList.
 * When recyclerview scrollToPosition is scroll to firstVisibleItem by viewPagerChangeCallback,
 * thirdVisibleDayPosition is attached with + 2 for get thirdVisibleItem position.
 * If created disposables by workoutsFragment to stay in CollectionWorkoutsViewModel then it causes
 * memory leak when WorkoutsFragment destroyed.
 * @property _binding This property is only valid between onCreateView and onDestroyView.
 * Binding instance is unnecessary to stay in memory between out this lifecycle methods.
 * @property binding Instead using null check while each time using _binding,
 * binding property has null check itself so it can be used when using view instances.
 */
class WorkoutsFragment : Fragment() {

    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding ?: throw Exception("Binding class is not found.")
    private var _thirdVisibleDayPosition: Int? = null
    private val thirdVisibleDayPosition
        get() = _thirdVisibleDayPosition ?: throw Exception("thirdVisibleDayPosition is not found.")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _thirdVisibleDayPosition = arguments?.getInt("position")
        Log.i(this.javaClass.simpleName, "Created fragment position: $thirdVisibleDayPosition")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManagerVertical =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val workoutsViewModel =
            ViewModelProvider(
                this,
                WorkoutsViewModelFactory(
                    AppDatabase.getInstance(requireActivity().application)
                )
            ).get(WorkoutsViewModel::class.java)
        val collectionWorkoutsViewModel =
            ViewModelProvider(requireActivity()).get(CollectionWorkoutsViewModel::class.java)

        val workoutsAdapter = WorkoutsAdapter(parentFragmentManager, this)
        val day = collectionWorkoutsViewModel
            .getDays()[thirdVisibleDayPosition]

        Log.i(this.javaClass.simpleName, "Created fragment day: ${day.dayNumber}")

        binding.workoutsRecyclerview.layoutManager = linearLayoutManagerVertical
        binding.workoutsRecyclerview.adapter = workoutsAdapter

        workoutsViewModel
            .subscribeWorkoutsWithExercisesInDb(day)

        /**
         * It has condition for check to size of workout list in removedInDbLiveData to check to
         * whether workout deleted in db.
         * If the condition is false, then it will prevent to calling the notifyDataSetChanged for not
         * break remove animation of recyclerview on the page the user sees.
         */
        workoutsViewModel
            .workoutListLiveData
            .observe(viewLifecycleOwner, { workoutList ->
                val workoutListSizeBeforeRemoved = workoutsViewModel
                    .updateWorkoutListAfterWorkoutRemovedInDbLiveData
                    .value

                if (workoutList.size >= workoutListSizeBeforeRemoved?.second ?: 0 || !isResumed) {
                    workoutsAdapter.setWorkoutList(workoutList)
                }
            })

        workoutsViewModel
            .updateWorkoutListAfterWorkoutRemovedInDbLiveData
            .observe(viewLifecycleOwner, { (position, workoutListSize) ->
                workoutsAdapter.removeWorkoutByPositionAndUpdateWorkoutList(
                    position,
                    workoutListSize
                )
                workoutsViewModel.deleteWorkoutWithExercisesDisposable.clear()
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(this.javaClass.simpleName, "Destroyed fragment position: $thirdVisibleDayPosition")
    }

    companion object {
        fun newInstance(thirdVisibleDayPosition: Int): WorkoutsFragment {
            val workoutsFragment = WorkoutsFragment()
            val args = Bundle()

            args.putInt("position", thirdVisibleDayPosition)
            workoutsFragment.arguments = args

            return workoutsFragment
        }
    }
}