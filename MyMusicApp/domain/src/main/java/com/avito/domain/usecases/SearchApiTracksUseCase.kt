package com.avito.domain.usecases

import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.ApiTracksRepository
import kotlinx.coroutines.flow.Flow

class SearchApiTracksUseCase(private val repository: ApiTracksRepository) {
    suspend operator fun invoke(query:String): Flow<List<TrackInfo>> {
        return repository.getApiTracksBySearch(query)
    }
}