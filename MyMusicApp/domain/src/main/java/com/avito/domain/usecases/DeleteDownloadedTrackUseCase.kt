package com.avito.domain.usecases

import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.DownloadedTracksRepository
import kotlinx.coroutines.flow.Flow

class DeleteDownloadedTrackUseCase(private val repository:DownloadedTracksRepository) {
  suspend operator fun invoke(trackId:Long) {
      repository.deleteDownloadedTrack(trackId)
    }
}