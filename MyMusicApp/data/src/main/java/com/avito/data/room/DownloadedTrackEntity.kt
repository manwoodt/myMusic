package com.avito.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.avito.domain.model.Album
import com.avito.domain.model.Artist

@Entity(tableName = "downloaded_tracks")
data class DownloadedTrackEntity(
    @PrimaryKey val id:Long,
    val title: String,
    val preview: String,
    val artist: String,
    val album: String,
    val md5_image:String,
    val filePath:String? = null
)
