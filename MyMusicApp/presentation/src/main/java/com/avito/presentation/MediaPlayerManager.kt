package com.avito.presentation

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log

class MediaPlayerManager {

    private var mediaPlayer: MediaPlayer? = null
    var isPrepared = false
    private var trackUrl: String? = null
    private var currentPosition = 0

    init {
        Log.d("MediaPlayerManager", "Инициализация MediaPlayer")
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setOnPreparedListener {
                Log.d("MediaPlayerManager", "Трек подготовлен к воспроизведению")
                isPrepared = true
//                if (mediaPlayer?.isPlaying == false) { // Проверяем, не играет ли плеер
//                    mediaPlayer?.start() // Автоматически запускаем после подготовки, если не играет
//                }
            }
            setOnCompletionListener {
                Log.d("MediaPlayerManager", "Трек завершён")
                stop() // Останавливаем плеер, когда трек закончился
            }
            setOnErrorListener { _, what, extra ->
                Log.e("MediaPlayerManager", "Ошибка MediaPlayer: what=$what, extra=$extra")
                false
            }
        }
    }

    fun preparePlayer(url: String, onPrepared: () -> Unit = {}) {
        Log.d("MediaPlayerManager", "preparePlayer() вызван с URL: $url")
        if (trackUrl != url) { // Проверка на тот же источник
            Log.d("MediaPlayerManager", "Загружаем новый трек")
            mediaPlayer?.reset()
            trackUrl = url
            try {
                mediaPlayer?.setDataSource(url)
                mediaPlayer?.prepareAsync()
            } catch (e: Exception) {
                Log.e("MediaPlayerManager", "Ошибка при установке dataSource: ${e.message}")
            }
        } else if (isPrepared && mediaPlayer?.isPlaying == false) { // Проверяем на тот же трек и что не играет
            // Если плеер подготовлен и не воспроизводится, начинаем воспроизведение с текущей позиции
            mediaPlayer?.seekTo(currentPosition)
            mediaPlayer?.start()
        }
        mediaPlayer?.setOnPreparedListener {
            isPrepared = true
            onPrepared() // Уведомление о готовности плеера
        }
        Log.d("MediaPlayerManager", "Тот же трек, ничего не делаем")
    }

    fun start() {
        if (isPrepared && mediaPlayer?.isPlaying == false) { // Проверяем, что плеер подготовлен и не воспроизводится
            mediaPlayer?.seekTo(currentPosition) // Восстанавливаем позицию после паузы
            mediaPlayer?.start()
        }
    }

    fun pause() {
        if (isPrepared && mediaPlayer?.isPlaying == true) { // Проверяем, что плеер воспроизводит трек
            currentPosition = mediaPlayer?.currentPosition ?: 0
            mediaPlayer?.pause()
        }
    }

//    fun stop() {
//        if (isPrepared && mediaPlayer != null) {
//            mediaPlayer?.stop()
//            mediaPlayer?.reset()
//            isPrepared = false
//        }
//    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    // Получить текущую позицию трека
    fun getCurrentPosition(): Int = mediaPlayer?.currentPosition ?: 0

    // Получить длительность трека
    fun getDuration(): Int = mediaPlayer?.duration ?: 0
}
