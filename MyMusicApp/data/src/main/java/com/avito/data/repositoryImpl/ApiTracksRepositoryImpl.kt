package com.avito.data.repositoryImpl

import android.util.Log
import com.avito.data.api.DeezerApiService
import com.avito.data.mappers.toDomain
import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.ApiTracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiTracksRepositoryImpl(private val api: DeezerApiService):ApiTracksRepository {
    override suspend fun getApiTopTracks(): Flow<List<TrackInfo>> = flow {
        try {
            val response = api.getTopTracks().trackContainer.tracks.map { it.toDomain() }
            emit(response)
            Log.d("TracksRepositoryImpl","Загрузка прошла успешно ${response.toString()}")
        }
        catch (e:Exception){
            Log.d("TracksRepositoryImpl",e.message.toString())
        }


    }

    override suspend fun getApiTracksBySearch(query: String): Flow<List<TrackInfo>> = flow {
        try {
            val response = api.getTracksBySearch(query).tracks.map { it.toDomain() }
            Log.d("TracksRepositoryImpl","RESPONSE $response")
            emit(response)
        }
        catch (e:Exception){
            Log.d("TracksRepositoryImpl","ERROR ${e.message.toString()}")
        }

    }

}