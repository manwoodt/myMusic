package com.avito.domain.usecases

import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.TrackRepository

class GetTrackById(private val repository: TrackRepository) {
    suspend operator fun invoke(trackId:Long):TrackInfo{
        return repository.getTrackById(trackId)
    }
}