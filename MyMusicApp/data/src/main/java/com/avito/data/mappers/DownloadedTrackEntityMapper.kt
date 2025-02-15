package com.avito.data.mappers


import com.avito.data.room.DownloadedTrackEntity
import com.avito.domain.model.TrackInfo

fun DownloadedTrackEntity.toDomain(): TrackInfo {
    return TrackInfo(
        id = id,
        title= title,
        preview = preview ,
        cover = cover,
        artist = artist,
        album = album
    )
}


fun TrackInfo.toEntity(songPath:String?): DownloadedTrackEntity {
    return DownloadedTrackEntity(
        id = id,
        title= title,
        preview = preview ,
        cover = cover,
        artist = artist,
        album = album,
        filePath = songPath,
        isDownloaded = isDownloaded
    )
}