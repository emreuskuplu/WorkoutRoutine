package com.emre.android.workoutroutine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WorkoutFragment : Fragment() {

    private lateinit var workoutDaysRecyclerView: RecyclerView
    private lateinit var viewModel: WorkoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val workoutDaysAdapter = WorkoutDaysAdapter(viewModel.getWorkoutDays())

        workoutDaysRecyclerView = view.findViewById(R.id.workoutDaysRecyclerView)
        workoutDaysRecyclerView.layoutManager = linearLayoutManager
        workoutDaysRecyclerView.adapter = workoutDaysAdapter

        viewModel.subscribeWorkoutDayClicks(workoutDaysAdapter.workoutDayClickStream)
    }
}