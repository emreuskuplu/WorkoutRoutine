package com.emre.android.workoutroutine.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.view.lists.adapters.WorkoutListAdapter
import com.emre.android.workoutroutine.viewmodel.CollectionWorkoutViewModel

class WorkoutFragment : Fragment() {

    private lateinit var workoutsRecyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManagerVertical = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val collectionWorkoutViewModel = ViewModelProvider(requireActivity()).get(CollectionWorkoutViewModel::class.java)

/*
        val workoutList = mutableListOf<Pair<String, List<String>>>()
        val list = listOf("Jump rope", "Bodyweight Squats", "Bench Press", "Push Ups")

        for (i in 0..4) {
            workoutList.add("Strength Workout" to list)
        }
*/
        val workoutsAdapter = WorkoutListAdapter(listOf())

        workoutsRecyclerView = view.findViewById(R.id.workouts_recyclerview)
        workoutsRecyclerView.layoutManager = linearLayoutManagerVertical
        workoutsRecyclerView.adapter = workoutsAdapter
/*
        collectionWorkoutViewModel.getWorkoutList().observe(this as LifecycleOwner, { workoutList ->
            workoutsAdapter.setWorkoutList(workoutList.map { it.workoutName to listOf()})
            workoutsAdapter.notifyDataSetChanged()
        })*/
/*
        collectionWorkoutViewModel.workoutListLiveData.observe(this as LifecycleOwner, { pair ->
            workoutsAdapter.setWorkoutList(pair.first.map { workoutList -> workoutList.workoutName to pair.second.map { it.exerciseName } })
        })*/

        collectionWorkoutViewModel.workoutListLiveData.observe(this as LifecycleOwner, {
            workoutsAdapter.setWorkoutList(it)
            workoutsAdapter.notifyDataSetChanged()
        })
    }
}