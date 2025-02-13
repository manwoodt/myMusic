package com.avito.presentation.UI

import android.os.Bundle
import com.avito.presentation.viewmodels.ApiTracksViewModel
import com.avito.tracks.TracksFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApiTracksFragment() : TracksFragment() {

    override val viewModel: ApiTracksViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}