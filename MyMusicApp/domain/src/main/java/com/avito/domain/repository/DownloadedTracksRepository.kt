package com.avito.domain.repository

import com.avito.domain.model.TrackInfo
import kotlinx.coroutines.flow.Flow

interface DownloadedTracksRepository {
    suspend fun getDownloadedTracks(): Flow<List<TrackInfo>>
    suspend fun downloadTrack(track: TrackInfo)
    suspend fun deleteDownloadedTrack(trackId: Long)
    suspend fun searchDownloadedTracks(query:String): List<TrackInfo>
}