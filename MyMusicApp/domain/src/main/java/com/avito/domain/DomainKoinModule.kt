package com.avito.domain

import com.avito.domain.usecases.DeleteDownloadedTrackUseCase
import com.avito.domain.usecases.DownloadTrackUseCase
import com.avito.domain.usecases.GetApiTopTracksUseCase
import com.avito.domain.usecases.GetDownloadedTracksUseCase
import com.avito.domain.usecases.GetTrackById
import com.avito.domain.usecases.SearchDownloadedTracksUseCase
import com.avito.domain.usecases.SearchApiTracksUseCase

import org.koin.dsl.module

val domainModule = module {
    factory { GetApiTopTracksUseCase(get()) }
    factory { SearchApiTracksUseCase(get()) }

    factory { DownloadTrackUseCase(get()) }
    factory { DeleteDownloadedTrackUseCase(get()) }
    factory { SearchDownloadedTracksUseCase(get()) }
    factory { GetDownloadedTracksUseCase(get()) }

    factory { GetTrackById(get()) }

}