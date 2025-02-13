package com.avito.domain.repository

import com.avito.domain.model.TrackInfo
import kotlinx.coroutines.flow.Flow


interface TracksRepository {
    suspend fun getApiTopTracks(): Flow<List<TrackInfo>>
    suspend fun getApiTracksBySearch(query:String): Flow<List<TrackInfo>>
}