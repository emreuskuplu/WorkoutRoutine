package com.emre.android.workoutroutine.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.WorkoutViewModel
import com.emre.android.workoutroutine.view.lists.adapters.WorkoutDayListAdapter
import com.emre.android.workoutroutine.view.lists.adapters.WorkoutListAdapter

class WorkoutFragment : Fragment() {

    private lateinit var workoutDaysRecyclerView: RecyclerView
    private lateinit var workoutsRecyclerView: RecyclerView
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var monthTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        workoutViewModel.monthLiveData.observe(this, {
            monthTextView.text = it
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManagerHorizontal = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val linearLayoutManagerVertical = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val workoutDaysAdapter = WorkoutDayListAdapter(workoutViewModel.getWorkoutDays())

        monthTextView = view.findViewById(R.id.month)

        val workoutList = mutableListOf<Pair<String, List<String>>>()
        val list = listOf("Jump rope", "Bodyweight Squats", "Bench Press", "Push Ups")

        for (i in 0..4) {
            workoutList.add("Strength Workout" to list)
        }

        val workoutsAdapter = WorkoutListAdapter(workoutList)

        workoutDaysRecyclerView = view.findViewById(R.id.workout_days_recyclerview)
        workoutDaysRecyclerView.layoutManager = linearLayoutManagerHorizontal
        workoutDaysRecyclerView.adapter = workoutDaysAdapter

        workoutsRecyclerView = view.findViewById(R.id.workouts_recyclerview)
        workoutsRecyclerView.layoutManager = linearLayoutManagerVertical
        workoutsRecyclerView.adapter = workoutsAdapter

        val workoutDayClickObservable = workoutDaysAdapter.workoutDayClickObservable

        workoutViewModel.subscribeWorkoutDayClick(workoutDayClickObservable)
    }
}