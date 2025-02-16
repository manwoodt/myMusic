package com.avito.presentation

import android.media.AudioAttributes
import android.media.MediaPlayer

class MediaPlayerManager {

    private var mediaPlayer: MediaPlayer? = null
    private var isPrepared = false
    private var trackUrl: String? = null
    private var currentPosition = 0 // Для хранения текущей позиции в треке

    private fun isPlaying(): Boolean = mediaPlayer?.isPlaying == true

    init {
        // Инициализация плеера
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setOnPreparedListener {
                isPrepared = true
                if (isPlaying) start()
            }
        }
    }



    fun preparePlayer(url: String, onPrepared: () -> Unit = {}) {
        if (trackUrl != url) { // Проверка на тот же источник
            mediaPlayer?.reset()
            trackUrl = url
            mediaPlayer?.setDataSource(url)
            mediaPlayer?.prepareAsync()
        }
        onPrepared()
    }

    fun start() {
        if (isPrepared && !isPlaying()) {
            mediaPlayer?.start()
            mediaPlayer?.seekTo(currentPosition) // Восстанавливаем позицию после паузы
        }
    }

    fun pause() {
        if (isPrepared && isPlaying()) {
            currentPosition = mediaPlayer?.currentPosition ?: 0
            mediaPlayer?.pause()
        }
    }

    fun stop() {
        if (isPrepared && mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            isPrepared = false
        }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }


    // Получить текущую позицию трека
    fun getCurrentPosition(): Int = mediaPlayer?.currentPosition ?: 0

    // Получить длительность трека
    fun getDuration(): Int = mediaPlayer?.duration ?: 0

}

