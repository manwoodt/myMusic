package com.avito.data.repositoryImpl

import android.content.Context
import com.avito.data.mappers.toDomain
import com.avito.data.mappers.toEntity
import com.avito.data.room.DownloadedTrackDao
import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.DownloadedTracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class DownloadedTracksRepositoryImpl(
    private val dao: DownloadedTrackDao
) : DownloadedTracksRepository {
    override suspend fun downloadTrack(track: TrackInfo)  {
      return dao.insertDownloadedTrack(track.toEntity())
    }

    override suspend fun deleteDownloadedTrack(trackId: Long) {
        dao.deleteDownloadedTrack(trackId)
    }

    override suspend fun searchDownloadedTracks(query: String): List<TrackInfo> {
        return dao.searchDownloadedTracks(query).map { it.toDomain() }
    }

    override suspend fun getDownloadedTracks(): Flow<List<TrackInfo>> {
        return dao.getAllDownloadedTracks().map { list -> list.map { it.toDomain() } }
    }


}