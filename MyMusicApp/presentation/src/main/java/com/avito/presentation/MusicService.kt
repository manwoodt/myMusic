package com.avito.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.media3.exoplayer.ExoPlayer
import com.avito.presentation.UI.PlaybackActivity
import com.avito.presentation.UI.PlaybackFragment
import org.koin.android.ext.android.inject

class MusicService : LifecycleService() {

    private val exoPlayer: ExoPlayer by inject()

    override fun onCreate() {
        super.onCreate()

        Log.d("MusicService", "MusicService onCreate() called, starting foreground")
        createNotificationChannelIfNeeded()
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)
        Log.d("MusicService", "Notification started as foreground service")
    }

    private fun createNotificationChannelIfNeeded() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music Playback",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

    }


    private fun createNotification(): Notification {
        val intent = Intent(this, PlaybackActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        Log.d("MusicService", "Notification created successfully")

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Воспроизведение музыки")
            .setContentText("Сейчас играет музыка")
            .setSmallIcon(R.drawable.ic_play) // Убедитесь, что этот ресурс существует
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    override fun onDestroy() {
        Log.d("MusicService", "MusicService destroyed")
        exoPlayer.stop()
        exoPlayer.release()
        super.onDestroy()
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "music_channel" // Не забудьте создать этот канал (NotificationChannel) в Application
    }

}