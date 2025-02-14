package com.avito.presentation.UI

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.avito.presentation.viewmodels.ApiTracksViewModel
import com.avito.tracks.TracksFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApiTracksFragment() : TracksFragment() {

    override val viewModel: ApiTracksViewModel by viewModel()

//    override fun loadTracks() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            Log.d("ApiTracksFragment", "Начинаю загрузку треков")
//            viewModel.loadTracks()
//
//            // Ждём, когда tracks обновится
//            viewModel.tracks.collect { tracks ->
//                Log.d("ApiTracksFragment", "Загрузка завершена")
//                Log.d("ApiTracksFragment", tracks.toString())
//            }
//        }
//    }

}

