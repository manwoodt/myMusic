package com.avito.data.api

import com.avito.data.model.TrackInfoDto
import com.avito.domain.model.TrackInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApiService{
    @GET("chart")
    suspend fun getTopTracks():TrackListResponse

    @GET("search")
    suspend fun getTracksBySearch( @Query("q") query:String):TrackContainer

    @GET("track/{trackId}")
    suspend fun getTrackById(@Path("trackId") trackId:Long): TrackInfoDto
}