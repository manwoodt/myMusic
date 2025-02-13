package com.avito.tracks

import com.avito.domain.model.TrackInfo
import kotlinx.coroutines.flow.Flow

interface TracksViewModel {
    val tracks: Flow<List<TrackInfo>>
    suspend fun loadTracks()
    suspend fun searchTracks(query:String)
}