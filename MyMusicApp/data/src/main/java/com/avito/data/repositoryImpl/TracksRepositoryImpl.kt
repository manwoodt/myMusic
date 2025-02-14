package com.avito.data.repositoryImpl

import android.util.Log
import com.avito.data.api.DeezerApiService
import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TracksRepositoryImpl(private val api: DeezerApiService):TracksRepository {
    override suspend fun getApiTopTracks(): Flow<List<TrackInfo>> = flow {
        try {
            val response = api.getTopTracks().trackContainer.tracks
            emit(response)
            Log.d("TracksRepositoryImpl","Загрузка прошла успешно ${response.toString()}")
        }
        catch (e:Exception){
            Log.d("TracksRepositoryImpl",e.message.toString())
        }


    }

    override suspend fun getApiTracksBySearch(query: String): Flow<List<TrackInfo>> = flow {
        try {
            val response = api.getTracksBySearch(query).tracks
            Log.d("TracksRepositoryImpl","RESPONSE $response")
            emit(response)

        }
        catch (e:Exception){
            Log.d("TracksRepositoryImpl","ERROR ${e.message.toString()}")
        }

    }

}