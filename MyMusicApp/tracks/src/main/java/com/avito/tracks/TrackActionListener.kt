package com.avito.tracks

import com.avito.domain.model.TrackInfo

interface TrackActionListener {
    fun onDownloadClick(track: TrackInfo)
    fun onDeleteClick(trackId: Long)
}