package com.avito.data.repositoryImpl

import android.util.Log
import com.avito.data.api.DeezerApiService
import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.TrackRepository

class TrackRepositoryImpl(private val api: DeezerApiService) : TrackRepository {
    override suspend fun getTrackById(trackId: Long): TrackInfo {
        val response = api.getTrackById(trackId)
        Log.d("TrackRepositoryImpl", response.toString())
        return response
    }
}