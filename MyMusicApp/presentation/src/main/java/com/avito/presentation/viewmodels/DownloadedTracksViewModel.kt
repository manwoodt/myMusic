package com.avito.presentation.viewmodels

import android.util.Log
import androidx.collection.emptyLongSet
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.DownloadedTracksRepository
import com.avito.domain.usecases.GetApiTopTracksUseCase
import com.avito.domain.usecases.SearchTracksUseCase
import com.avito.tracks.TracksViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DownloadedTracksViewModel(
private val repository: DownloadedTracksRepository
) : ViewModel(), TracksViewModel {

    private val _tracks = MutableStateFlow<List<TrackInfo>>(emptyList())
    override val tracks: StateFlow<List<TrackInfo>> = _tracks.asStateFlow()

    // загрузка песен из памяти телефона
    override suspend fun loadTracks() {
        viewModelScope.launch {
            try {
                repository.getDownloadedTracks().collect {
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
                repository.searchDownloadedTracks(query).collect {
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

    fun downloadTrack(track: TrackInfo) {
        viewModelScope.launch {
            repository.downloadTrack(track)
        }
    }

    fun deleteTrack(trackId: Long) {
        viewModelScope.launch {
            repository.deleteDownloadedTrack(trackId)
        }
    }

}