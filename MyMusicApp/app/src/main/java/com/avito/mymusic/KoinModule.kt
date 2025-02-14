package com.avito.mymusic

import com.avito.data.repositoryImpl.ApiTracksRepositoryImpl
import com.avito.domain.repository.ApiTracksRepository
import com.avito.domain.usecases.GetApiTopTracksUseCase
import com.avito.presentation.viewmodels.ApiTracksViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val tracksModule = module{
    single<ApiTracksRepository>{ApiTracksRepositoryImpl(get())}
    single { GetApiTopTracksUseCase(get()) }
    viewModel { ApiTracksViewModel(get(), get()) }
}