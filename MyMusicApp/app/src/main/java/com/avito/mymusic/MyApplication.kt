package com.avito.mymusic

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.media3.exoplayer.ExoPlayer
import com.avito.data.dataModule
import com.avito.domain.domainModule
import com.avito.presentation.MusicService
import com.avito.presentation.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(dataModule, domainModule, presentationModule)
        }
    }



}