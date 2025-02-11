package com.avito.data.api

import com.avito.domain.model.TrackInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerApiService{
    @GET("chart")
    suspend fun getTopTracks():List<TrackInfo>

    @GET("search")
    suspend fun getTrack( @Query("q") query:String):List<TrackInfo>

}