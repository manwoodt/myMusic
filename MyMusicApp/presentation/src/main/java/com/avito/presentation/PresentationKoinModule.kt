package com.avito.presentation

import com.avito.presentation.viewmodels.ApiTracksViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { ApiTracksViewModel(get(), get()) }
}