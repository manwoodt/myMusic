package com.avito.data.mappers


import com.avito.data.model.TrackInfoDto
import com.avito.domain.model.TrackInfo


fun TrackInfoDto.toDomain():TrackInfo{
    return TrackInfo(
        id = id,
        title= title,
        preview = preview ,
        cover =  "https://e-cdns-images.dzcdn.net/images/cover/$md5_image/100x100-000000-80-0-0.jpg" ,
        artist = artist.name,
        album = album.title
    )
}