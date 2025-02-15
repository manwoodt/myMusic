package com.avito.presentation.UI

import com.avito.presentation.viewmodels.DownloadedTracksViewModel
import com.avito.tracks.TracksFragment

class DownloadedTracksFragment() : TracksFragment() {
    override val viewModel: DownloadedTracksViewModel by viewModel()

}