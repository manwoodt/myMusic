package com.avito.domain.usecases

import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.DownloadedTracksRepository
import kotlinx.coroutines.flow.Flow

class DownloadTrackUseCase(private val repository:DownloadedTracksRepository) {
  suspend operator fun invoke(track:TrackInfo) {
      repository.downloadTrack(track)
    }
}