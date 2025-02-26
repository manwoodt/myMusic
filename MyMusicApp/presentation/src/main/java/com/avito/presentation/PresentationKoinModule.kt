package com.avito.presentation

import androidx.media3.exoplayer.ExoPlayer
import com.avito.presentation.viewmodels.ApiTracksViewModel
import com.avito.presentation.viewmodels.DownloadedTracksViewModel
import com.avito.presentation.viewmodels.PlaybackViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    //   single { ExoPlayer.Builder(get()).build() }
    viewModel { ApiTracksViewModel(get(), get(), get()) }
    viewModel { DownloadedTracksViewModel(get(), get(), get()) }
    viewModel { PlaybackViewModel(get(), get()) }

    single {
        ExoPlayer.Builder(androidContext()).build()
    }
}