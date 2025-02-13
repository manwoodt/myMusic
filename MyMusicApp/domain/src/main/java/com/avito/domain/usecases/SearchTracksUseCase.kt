package com.avito.domain.usecases

import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.TracksRepository
import kotlinx.coroutines.flow.Flow

class SearchTracksUseCase(private val repository: TracksRepository) {
    suspend operator fun invoke(query:String): Flow<List<TrackInfo>> {
        return repository.getApiTracksBySearch(query)
    }
}