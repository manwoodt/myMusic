package com.avito.data.model

data class TrackInfoDto(
    val id:Long,
    val title: String,
    val preview: String,
    val artist: ArtistDto,
    val album: AlbumDto,
    val md5_image:String
)
