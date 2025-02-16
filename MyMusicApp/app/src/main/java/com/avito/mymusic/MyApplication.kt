package com.avito.mymusic

import android.app.Application
import androidx.media3.exoplayer.ExoPlayer
import com.avito.data.dataModule
import com.avito.domain.domainModule
import com.avito.presentation.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {
    private lateinit var player: ExoPlayer

    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(this).build()
        startKoin {
            androidContext(this@MyApplication)
            modules(dataModule, domainModule, presentationModule, module { single { player } })
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        player.release()
    }

    override fun onTerminate() {
        super.onTerminate()
        player.release()
    }
}