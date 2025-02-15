package com.avito.domain.repository

import com.avito.domain.model.TrackInfo

interface TrackRepository {
  suspend fun getTrackById(trackId:Long):TrackInfo
}