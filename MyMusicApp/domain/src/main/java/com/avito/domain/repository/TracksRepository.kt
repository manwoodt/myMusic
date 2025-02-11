package com.avito.domain.repository

import com.avito.domain.model.TrackInfo

interface TracksRepository {
    suspend fun getApiTracks(): List<TrackInfo>
}