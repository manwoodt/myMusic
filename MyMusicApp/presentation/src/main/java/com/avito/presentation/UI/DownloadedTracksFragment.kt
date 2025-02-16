package com.avito.presentation.UI

import androidx.navigation.fragment.findNavController
import com.avito.presentation.viewmodels.DownloadedTracksViewModel
import com.avito.tracks.TracksFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DownloadedTracksFragment() : TracksFragment() {
    override val viewModel: DownloadedTracksViewModel by viewModel()
    override val isDownloadedScreen: Boolean = true
    override val onTrackClicked: (Long) -> Unit = { trackId ->
        val action = DownloadedTracksFragmentDirections.actionGlobalToPlaybackFragment(trackId)
        findNavController().navigate(action)
    }
}