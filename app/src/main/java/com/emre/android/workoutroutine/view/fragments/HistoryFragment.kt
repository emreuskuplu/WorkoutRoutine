package com.emre.android.workoutroutine.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.emre.android.workoutroutine.databinding.FragmentHistoryBinding
import com.emre.android.workoutroutine.viewmodel.MainViewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding ?: throw Exception("Binding is not found")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.run {
            toolbarLiveData.value = false
            bottomBarLiveData.value = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}