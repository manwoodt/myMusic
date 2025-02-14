package com.avito.data.api

import com.avito.domain.model.TrackInfo
import com.google.gson.annotations.SerializedName

data class TrackListResponse(
    @SerializedName("tracks") val trackContainer: TrackContainer
)

data class TrackContainer(
    @SerializedName("data") val tracks: List<TrackInfo>
)
