package com.avito.domain.usecases

import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.ApiTracksRepository
import kotlinx.coroutines.flow.Flow

class GetApiTopTracksUseCase(private val repository: ApiTracksRepository) {
    suspend operator fun invoke(): Flow<List<TrackInfo>> {
        val result = repository.getApiTopTracks()
        return result
    }
}