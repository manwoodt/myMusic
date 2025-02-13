package com.avito.tracks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.avito.tracks.databinding.FragmentTracksBinding
import kotlinx.coroutines.launch

abstract class TracksFragment : Fragment() {

    protected abstract val viewModel: TracksViewModel
    protected lateinit var adapter: TracksAdapter

    private var _binding: FragmentTracksBinding? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        lifecycleScope.launch {
            viewModel.loadTracks()
        }
    }

    private fun setupRecyclerView() {
        adapter = TracksAdapter()
        binding.rvTracks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTracks.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}