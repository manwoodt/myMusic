package com.avito.domain.usecases

import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.DownloadedTracksRepository
import kotlinx.coroutines.flow.Flow

class SearchDownloadedTracksUseCase(private val repository:DownloadedTracksRepository) {
  suspend operator fun invoke(query:String): Flow<List<TrackInfo>> {
       return repository.searchDownloadedTracks(query)
    }
}