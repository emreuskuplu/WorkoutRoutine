package com.emre.android.workoutroutine.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.view.lists.adapters.WorkoutListAdapter

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

        val workoutList = mutableListOf<Pair<String, List<String>>>()
        val list = listOf("Jump rope", "Bodyweight Squats", "Bench Press", "Push Ups")

        for (i in 0..4) {
            workoutList.add("Strength Workout" to list)
        }

        val workoutsAdapter = WorkoutListAdapter(workoutList)

        workoutsRecyclerView = view.findViewById(R.id.workouts_recyclerview)
        workoutsRecyclerView.layoutManager = linearLayoutManagerVertical
        workoutsRecyclerView.adapter = workoutsAdapter
    }
}