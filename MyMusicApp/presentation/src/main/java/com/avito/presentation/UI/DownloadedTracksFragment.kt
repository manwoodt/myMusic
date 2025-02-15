package com.avito.presentation.UI

import com.avito.presentation.viewmodels.DownloadedTracksViewModel
import com.avito.tracks.TracksFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DownloadedTracksFragment() : TracksFragment() {
    override val viewModel: DownloadedTracksViewModel by viewModel()

}