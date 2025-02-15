package com.avito.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avito.domain.model.TrackInfo
import com.avito.domain.usecases.DeleteDownloadedTrackUseCase
import com.avito.domain.usecases.DownloadTrackUseCase
import com.avito.domain.usecases.GetDownloadedTracksUseCase
import com.avito.domain.usecases.SearchDownloadedTracksUseCase
import com.avito.tracks.TracksViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DownloadedTracksViewModel(
    private val downloadTrackUseCase: DownloadTrackUseCase,
    private val deleteDownloadedTrackUseCase: DeleteDownloadedTrackUseCase,
    private val searchDownloadedTracksUseCase: SearchDownloadedTracksUseCase,
    private val getDownloadedTracksUseCase: GetDownloadedTracksUseCase
) : ViewModel(), TracksViewModel {

    private val _tracks = MutableStateFlow<List<TrackInfo>>(emptyList())
    override val tracks: StateFlow<List<TrackInfo>> = _tracks.asStateFlow()




    // загрузка песен из памяти телефона
    override suspend fun loadTracks() {
        viewModelScope.launch {
            try {
                getDownloadedTracksUseCase().collect {
                    Log.d("loadTracks from memory", it.toString())
                    _tracks.value = it
                }
            } catch (e: Exception) {
                println(e.message)
                Log.e("DownloadedTracksViewModel", "Ошибка загрузки треков", e)
            }
        }
    }

    // поиск в памяти телефона
    override suspend fun searchTracks(query: String) {
        viewModelScope.launch {
            try {
                searchDownloadedTracksUseCase(query).collect {
                    if (it.isNotEmpty()) {
                        Log.d("searchTracks from memory", it.toString())
                        _tracks.value = it
                    } else {
                        Log.e("DownloadedTracksViewModel", "Нет треков по запросу $query")
                    }
                }

            } catch (e: Exception) {
                Log.e("DownloadedTracksViewModel", "Ошибка поиска треков",e)
            }
        }
    }

    override suspend fun downloadTrack(track: TrackInfo) {
        viewModelScope.launch {
            downloadTrackUseCase(track)
            loadTracks()
        }
    }

    override suspend fun deleteTrack(trackId: Long) {
        viewModelScope.launch {
            deleteDownloadedTrackUseCase(trackId)
            loadTracks()
        }
    }

}