package com.avito.tracks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.avito.tracks.databinding.FragmentTracksBinding
import kotlinx.coroutines.launch

abstract class TracksFragment : Fragment() {

    protected abstract val viewModel: TracksViewModel
    protected lateinit var adapter: TracksAdapter

    private var _binding: FragmentTracksBinding? = null
    protected val binding get() = _binding!!

    protected abstract val isDownloadedScreen: Boolean

    protected abstract val  onTrackClicked: ((Long) -> Unit)


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
            viewModel.tracks.collect { newTracks ->
                Log.d("TracksFragment", "Получены новые треки: ${newTracks.size}")
                adapter.submitList(newTracks)
            }
        }
        setupSearchView()
    }

    private fun setupSearchView() {
        binding.svTracks.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    lifecycleScope.launch {
                        Log.d("setupSearchButton", "Текст изменился : $newText")
                        viewModel.searchTracks(newText)
                    }
                } else {
                    lifecycleScope.launch {
                        viewModel.loadTracks()
                    }
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    lifecycleScope.launch {
                        Log.d("setupSearchButton", "Запрос: $query")
                        viewModel.searchTracks(query)
                    }
                }
                return true
            }
        })

    }

    private fun setupRecyclerView() {
        adapter = TracksAdapter(
            isDownloadedScreen,
            actionWithTrack = {
                lifecycleScope.launch {
                    (viewModel.actionWithTrack(it))
                }
            },
            onTrackClick = { trackId ->
                onTrackClicked?.invoke(trackId)
            })
        binding.rvTracks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTracks.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}