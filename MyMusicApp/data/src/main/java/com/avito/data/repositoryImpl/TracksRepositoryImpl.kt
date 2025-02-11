package com.avito.data.repositoryImpl

import com.avito.data.api.DeezerApiService
import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.TracksRepository

class TracksRepositoryImpl(private val api: DeezerApiService):TracksRepository {
    override suspend fun getApiTracks(): List<TrackInfo> {
      return  api.getTopTracks()
    }

}