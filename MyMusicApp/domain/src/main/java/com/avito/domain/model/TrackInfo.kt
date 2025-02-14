package com.avito.domain.model

data class TrackInfo(
    val id:Long,
    val title: String,
    val preview: String,
    val artist: Artist,
    val album: Album,
    val md5_image:String
)


