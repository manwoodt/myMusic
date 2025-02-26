package com.avito.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.avito.domain.model.TrackInfo
import com.avito.domain.usecases.GetTrackByIdUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PlaybackViewModel(
    private val getTrackByIdUseCase: GetTrackByIdUseCase,
    private val player: ExoPlayer,
) : ViewModel() {

    var startMusicService: (() -> Unit)? = null

    private val _trackInfo = MutableStateFlow<TrackInfo?>(null)
    val trackInfo: StateFlow<TrackInfo?> get() = _trackInfo

    private val _playbackState = MutableStateFlow(false)
    val playbackState: StateFlow<Boolean> get() = _playbackState

    private val _progress = MutableStateFlow(0)
    val progress: StateFlow<Int> = _progress


    private val _duration = MutableStateFlow(0)
    val duration: StateFlow<Int> = _duration



    init {
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    _duration.value = player.duration.toInt()
                }
                _playbackState.value = player.isPlaying
            }
        })

        viewModelScope.launch {
            while (isActive) {
                _progress.value = player.currentPosition.toInt()
                delay(1000)
            }
        }
    }


    fun loadTrackbyId(id: Long) {
        viewModelScope.launch {
            val track = getTrackByIdUseCase(id)
            Log.d("PlaybackViewModel", "Track loaded: $track")
            _trackInfo.value = track
            track.let {
                player.stop()
                player.clearMediaItems()
                val mediaItem = MediaItem.fromUri(it.preview)
                Log.d("PlaybackViewModel", "MediaItem set: ${it.preview}")
                player.setMediaItem(mediaItem)
                player.prepare()

                Log.d("PlaybackViewModel", "Calling player.play() now!")
                _duration.value = player.duration.toInt()
                Log.d("PlaybackViewModel", "Attempting to start MusicService")
                startMusicService?.invoke()
                player.play()
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

    fun seekTo(position: Int) {
        player.seekTo(position.toLong())
        _progress.value = position
    }


    override fun onCleared() {
        Log.d("PlaybackViewModel", "ViewModel cleared, stopping player")
        player.stop()
        player.clearMediaItems()
        super.onCleared()
    }

}