package com.avito.domain.usecases

import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.TracksRepository
import kotlinx.coroutines.flow.Flow

class GetApiTopTracksUseCase(private val repository: TracksRepository) {
    suspend operator fun invoke(): Flow<List<TrackInfo>> {
        val result = repository.getApiTopTracks()
        return result
    }
}