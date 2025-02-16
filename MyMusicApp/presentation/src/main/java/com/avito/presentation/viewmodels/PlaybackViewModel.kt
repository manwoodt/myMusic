package com.avito.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.avito.domain.model.TrackInfo
import com.avito.domain.usecases.GetTrackByIdUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaybackViewModel(
    private val getTrackByIdUseCase: GetTrackByIdUseCase,
    private val player: ExoPlayer
) : ViewModel() {

    private val _trackInfo = MutableStateFlow<TrackInfo?>(null)
    val trackInfo: StateFlow<TrackInfo?> get() = _trackInfo

    private val _playbackState = MutableStateFlow(false)
    val playbackState: StateFlow<Boolean> get() = _playbackState

    private val _progress = MutableStateFlow(0) // Progress for seeking
    val progress: StateFlow<Int> = _progress


    private val _duration = MutableStateFlow(0) // Длительность трека
    val duration: StateFlow<Int> = _duration


    private var progressTimer: Job? = null

    init {
        // Обработка изменений состояния воспроизведения
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                super.onPlaybackStateChanged(state)
                if (state == Player.STATE_READY) {
                    _duration.value = player.duration.toInt()
                }
                _playbackState.value = player.isPlaying
                if (player.isPlaying) {
                    startProgressTimer()
                } else {
                    stopProgressTimer()
                }
            }

            // Если требуется, можно обработать другие события
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                _playbackState.value = isPlaying
            }
        })
    }

    private fun startProgressTimer() {
        // Запускаем таймер для отслеживания прогресса каждую секунду
        progressTimer = viewModelScope.launch {
            while (true) {
                delay(1000) // Каждую секунду
                _progress.value = player.currentPosition.toInt()
            }
        }
    }

    private fun stopProgressTimer() {
        progressTimer?.cancel() // Останавливаем таймер
        progressTimer = null
    }


    fun loadTrackbyId(id: Long) {
        viewModelScope.launch {
            val track = getTrackByIdUseCase(id)
            Log.d("PlaybackViewModel", "Track loaded: $track")
            _trackInfo.value = track
            track?.let {
                player.stop()
                player.clearMediaItems()
                val mediaItem = MediaItem.fromUri(it.preview)
                Log.d("PlaybackViewModel", "MediaItem set: ${it.preview}")
                player.setMediaItem(mediaItem)
                player.prepare()

                Log.d("PlaybackViewModel", "Calling player.play() now!")
                _duration.value = player.duration.toInt()
                player.play()
              //  _playbackState.value = true
            }
        }
    }

    fun playPause() {
        Log.d("PlaybackViewModel", "playPause() called, isPlaying: ${player.isPlaying}")
        if (player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
        _playbackState.value = player.isPlaying
    }

    fun seekTo(position: Long) {
        player.seekTo(position)
    }


    override fun onCleared() {
        Log.d("PlaybackViewModel", "ViewModel cleared, stopping player")
        player.stop()
        player.clearMediaItems()
        super.onCleared()
    }


}