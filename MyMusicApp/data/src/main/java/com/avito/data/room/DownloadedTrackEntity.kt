package com.avito.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_tracks")
data class DownloadedTrackEntity(
    @PrimaryKey val id:Long,
    val title: String,
    val preview: String,
    val artist: String,
    val album: String,
    val cover:String,
    val filePath:String? = null
)
