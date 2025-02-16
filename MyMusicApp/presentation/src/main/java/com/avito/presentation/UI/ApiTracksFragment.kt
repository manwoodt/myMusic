package com.avito.presentation.UI

import androidx.navigation.fragment.findNavController
import com.avito.presentation.viewmodels.ApiTracksViewModel
import com.avito.tracks.TracksFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApiTracksFragment() : TracksFragment() {
    override val viewModel: ApiTracksViewModel by viewModel()
    override val isDownloadedScreen: Boolean = false
    override val onTrackClicked: (Long) -> Unit = {trackId->
        val action = ApiTracksFragmentDirections.actionGlobalToPlaybackFragment(trackId)
    findNavController().navigate(action)
    }

}

