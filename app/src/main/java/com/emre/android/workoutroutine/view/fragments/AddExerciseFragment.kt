package com.emre.android.workoutroutine.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.emre.android.workoutroutine.databinding.FragmentAddExerciseBinding
import com.emre.android.workoutroutine.view.hideKeyboard
import com.emre.android.workoutroutine.viewmodel.MainViewModel

class AddExerciseFragment : Fragment() {

    private var _binding: FragmentAddExerciseBinding? = null
    private val binding get() = _binding ?: throw Exception("Binding is not found.")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.let {
            it.bottomBarLiveData.value = false
            it.onUserInteractionLiveData.observe(this as LifecycleOwner) {
                binding.exerciseNameTextInputLayout.editText?.let { editText ->
                    editText.isFocusable = false
                    editText.isFocusableInTouchMode = true
                }
                view.hideKeyboard()
            }
        }

        binding.let {
            it.basedOnSetRB.setOnClickListener {
                binding.basedOnTimeRB.isChecked = false
            }
            it.basedOnTimeRB.setOnClickListener {
                binding.basedOnSetRB.isChecked = false
            }
            it.backIB.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}