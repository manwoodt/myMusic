package com.avito.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.media3.exoplayer.ExoPlayer
import com.avito.presentation.UI.PlaybackActivity
import com.avito.presentation.viewmodels.PlaybackViewModel
import org.koin.android.ext.android.inject

class MusicService : LifecycleService() {

    private val exoPlayer: ExoPlayer by inject()
    private lateinit var notificationManager: NotificationManager
    private lateinit var mediaReceiver: BroadcastReceiver

    private var isPlaying = exoPlayer.isPlaying

    override fun onCreate() {
        super.onCreate()

        Log.d("MusicService", "MusicService onCreate() called, starting foreground")
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        registerMediaReceiver()
//        val notification = createNotification()
//        startForeground(NOTIFICATION_ID, notification)
//        Log.d("MusicService", "Notification started as foreground service")
    }





    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music Playback",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Управление воспроизведением музыки"
            }
            notificationManager.createNotificationChannel(channel)
        }

    }

    // Регистрация BroadcastReceiver для обработки медиадействий (на случай, если система отправит intent)
    private fun registerMediaReceiver() {
        mediaReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == ACTION_PLAY_PAUSE) {
                    handlePlayPause()
                }
            }
        }
        val filter = IntentFilter().apply {
            addAction(ACTION_PLAY_PAUSE)
        }
        registerReceiver(mediaReceiver, filter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        updateNotification()

        when (intent?.action){
//            ACTION_START_SERVICE->
//                updateNotification()
            ACTION_PLAY_PAUSE->
                handlePlayPause()
        }
      return START_STICKY
    }


    private fun updateNotification() {
        val notification = buildNotification()
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun buildNotification(): Notification {
        val trackTitle = PlaybackViewModel.currentTrackTitle ?: "Unknown Title"
        val trackArtist = PlaybackViewModel.currentTrackArtist ?: "Unknown Artist"

        val intent = Intent(this, PlaybackActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        Log.d("MusicService", "Notification created successfully")

        val playPauseIntent = PendingIntent.getService(
            this,
            0,
            Intent(this, MusicService::class.java).setAction(ACTION_PLAY_PAUSE),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val playPauseIcon = if (isPlaying)
            android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(trackTitle)
            .setContentText(trackArtist)
            .setSmallIcon(R.drawable.ic_play)
            .setContentIntent(pendingIntent)
            .addAction(playPauseIcon, "Play/Pause",playPauseIntent)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0)
            )
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }

    private fun handlePlayPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        } else {
            exoPlayer.play()
        }
        isPlaying = exoPlayer.isPlaying
        updateNotification()
    }

    override fun onDestroy() {
        Log.d("MusicService", "MusicService destroyed")
        unregisterReceiver(mediaReceiver)
        stopForeground(true)
        super.onDestroy()
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "music_channel"
        const val ACTION_START_SERVICE = "START_SERVICE"
        const val ACTION_PLAY_PAUSE = "PLAY_PAUSE"
    }

}