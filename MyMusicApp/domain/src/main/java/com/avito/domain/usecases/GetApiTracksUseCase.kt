package com.avito.domain.usecases

import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.TracksRepository

class GetApiTracksUseCase(val repository: TracksRepository) {
    suspend operator fun invoke():List<TrackInfo>{
        return repository.getApiTracks()
    }
}