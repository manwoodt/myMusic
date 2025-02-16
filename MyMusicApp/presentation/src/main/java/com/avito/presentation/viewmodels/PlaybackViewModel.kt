package com.avito.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avito.domain.model.TrackInfo
import com.avito.domain.usecases.GetTrackByIdUseCase
import com.avito.presentation.MediaPlayerManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaybackViewModel(
    private val getTrackByIdUseCase: GetTrackByIdUseCase
) : ViewModel() {

    private val _currentTrack = MutableStateFlow<TrackInfo?>(null)
    val currentTrack: StateFlow<TrackInfo?> = _currentTrack

    private val mediaPlayerManager = MediaPlayerManager()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition: StateFlow<Int> = _currentPosition

    private val _duration = MutableStateFlow(0)
    val duration: StateFlow<Int> = _duration


    fun loadTrackbyId(id: Long) {
        viewModelScope.launch {
            val track = getTrackByIdUseCase(id)
            _currentTrack.value = track
            track.preview.let { play(it)   }
        }
    }


    fun play(url: String) {
        mediaPlayerManager.preparePlayer(url) {
            mediaPlayerManager.start()
            _isPlaying.value = true
        }
    }

    fun pause() {
        mediaPlayerManager.pause()
        _isPlaying.value = false
    }

    fun stop() {
        mediaPlayerManager.stop()
        _isPlaying.value = false
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayerManager.release()
    }


}