package com.avito.domain

import com.avito.domain.usecases.GetApiTopTracksUseCase
import com.avito.domain.usecases.SearchTracksUseCase

import org.koin.dsl.module

val domainModule = module {
    factory { GetApiTopTracksUseCase(get()) }
    factory { SearchTracksUseCase(get()) }
}