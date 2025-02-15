package com.avito.presentation.UI

import com.avito.presentation.viewmodels.ApiTracksViewModel
import com.avito.tracks.TracksFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApiTracksFragment() : TracksFragment() {
    override val viewModel: ApiTracksViewModel by viewModel()
    override val isDownloadedScreen: Boolean = false
}

