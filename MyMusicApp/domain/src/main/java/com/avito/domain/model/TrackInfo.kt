package com.avito.domain.model

data class TrackInfo(
    val id:Int,
    val title: String,
    val preview: String,
    val picture: String,
    val artist: Artist,
    val album: Album
)


