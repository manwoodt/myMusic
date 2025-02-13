package com.avito.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avito.domain.model.TrackInfo
import com.avito.domain.usecases.GetApiTopTracksUseCase
import com.avito.domain.usecases.SearchTracksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ApiTracksViewModel(
    private val getChartTracksUseCase: GetApiTopTracksUseCase,
    private val searchTracksUseCase: SearchTracksUseCase
) : ViewModel() {

    private val _tracks = MutableStateFlow<List<TrackInfo>>(emptyList())
            val tracks: StateFlow<List<TrackInfo>> = _tracks.asStateFlow()

    fun getChartTracks(){
        viewModelScope.launch {
            try {
                getChartTracksUseCase().collect {_tracks.value = it}
            }
           catch (e:Exception){
               println(e.message)
           }
        }
    }

    fun searchTracks(query:String){
        viewModelScope.launch {
            try {
                searchTracksUseCase(query).collect {_tracks.value = it}
            }
            catch (e:Exception){
                println(e.message)
            }
        }
    }


}