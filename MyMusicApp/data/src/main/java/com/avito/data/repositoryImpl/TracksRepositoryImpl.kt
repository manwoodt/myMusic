package com.avito.data.repositoryImpl

import com.avito.data.api.DeezerApiService
import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TracksRepositoryImpl(private val api: DeezerApiService):TracksRepository {
    override suspend fun getApiTopTracks(): Flow<List<TrackInfo>> = flow {
      emit(api.getTopTracks())
    }

    override suspend fun getApiTracksBySearch(query: String): Flow<List<TrackInfo>> = flow {
        emit(api.getTracksBySearch(query))
    }

}