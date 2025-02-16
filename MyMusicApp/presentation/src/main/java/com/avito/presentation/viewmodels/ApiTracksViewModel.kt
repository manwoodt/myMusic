package com.avito.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avito.domain.model.TrackInfo
import com.avito.domain.usecases.DownloadTrackUseCase
import com.avito.domain.usecases.GetApiTopTracksUseCase
import com.avito.domain.usecases.SearchApiTracksUseCase
import com.avito.tracks.TracksViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ApiTracksViewModel(
    private val getChartTracksUseCase: GetApiTopTracksUseCase,
    private val searchApiTracksUseCase: SearchApiTracksUseCase,
    private val downloadTrackUseCase: DownloadTrackUseCase
) : ViewModel(), TracksViewModel {

    private val _tracks = MutableStateFlow<List<TrackInfo>>(emptyList())
    override val tracks: StateFlow<List<TrackInfo>> = _tracks.asStateFlow()


    init {
        viewModelScope.launch {
            loadTracks()
        }
    }

    // загрузка топа песен из интернета
    override suspend fun loadTracks() {
        viewModelScope.launch {
            try {
                getChartTracksUseCase().collect {
                    Log.d("loadTracks", it.toString())
                    _tracks.value = it
                }
            } catch (e: Exception) {
                println(e.message)
                Log.e("ApiTracksFragment", "Ошибка загрузки треков", e)
            }
        }
    }

    // поиск в интернете
    override suspend fun searchTracks(query: String) {
        viewModelScope.launch {
            try {
                searchApiTracksUseCase(query).collect {
                    if (it.isNotEmpty()) {
                        Log.d("searchTracks", it.toString())
                        _tracks.value = it
                    } else {
                        Log.e("searchTracks", "Нет треков по запросу $query")
                    }
                }


            } catch (e: Exception) {
                Log.d("searchTracks Error", e.message.toString())
            }
        }
    }

    override suspend fun actionWithTrack(track: TrackInfo) {
        viewModelScope.launch {
            downloadTrackUseCase(track)
            loadTracks()
        }
    }


}