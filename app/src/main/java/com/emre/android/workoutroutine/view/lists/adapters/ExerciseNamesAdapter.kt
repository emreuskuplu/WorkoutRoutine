package com.emre.android.workoutroutine.view.lists.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emre.android.workoutroutine.databinding.ItemExerciseNameBinding

class ExerciseNamesAdapter(private val exerciseNameList: List<String>) :
    RecyclerView.Adapter<ExerciseNamesAdapter.ExerciseNameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseNameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemExerciseNameBinding.inflate(layoutInflater, parent, false)
        return ExerciseNameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseNameViewHolder, position: Int) {
        holder.binding.exerciseName.text = exerciseNameList[position]
    }

    override fun getItemCount(): Int {
        return exerciseNameList.size
    }

    class ExerciseNameViewHolder(val binding: ItemExerciseNameBinding) :
        RecyclerView.ViewHolder(binding.root)
}