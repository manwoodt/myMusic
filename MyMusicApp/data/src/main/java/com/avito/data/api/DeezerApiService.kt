package com.avito.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerApiService{
    @GET("chart")
    suspend fun getTopTracks():TrackListResponse

    @GET("search")
    suspend fun getTracksBySearch( @Query("q") query:String):TrackListResponse

}