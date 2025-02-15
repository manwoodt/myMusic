package com.avito.data.repositoryImpl

import android.content.Context
import android.util.Log
import com.avito.data.mappers.toDomain
import com.avito.data.mappers.toEntity
import com.avito.data.room.DownloadedTrackDao
import com.avito.domain.model.TrackInfo
import com.avito.domain.repository.DownloadedTracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DownloadedTracksRepositoryImpl(
    private val context: Context,
    private val dao: DownloadedTrackDao
) : DownloadedTracksRepository {


    override suspend fun downloadTrack(track: TrackInfo)  {
        val filePath = savePreviewToFile(track)
        dao.insertDownloadedTrack(track.toEntity(filePath))
    }

    override suspend fun deleteDownloadedTrack(trackId: Long) {
        val track = dao.getDownloadedTrackById(trackId)
        track?.filePath?.let { deletePreviewFile(it) }
        dao.deleteDownloadedTrack(trackId)
    }

    override suspend fun searchDownloadedTracks(query: String): Flow<List<TrackInfo>> {
        return dao.searchDownloadedTracks(query).map { list -> list.map { it.toDomain() }}
    }

    override suspend fun getDownloadedTracks(): Flow<List<TrackInfo>> {
        return dao.getAllDownloadedTracks().map { list -> list.map { it.toDomain() } }
    }

    private suspend fun savePreviewToFile(track: TrackInfo):String?{
        return withContext(Dispatchers.IO){
            try {
                val file = File(context.filesDir, "${track.id}.mp3")
                val url = URL(track.preview)
                // открываем поток чтения данных из инета
                // .use автоматически закрывает поток после завершения
                url.openStream().use { songFromInternet->
                    FileOutputStream(file).use { localSong ->
                        songFromInternet.copyTo(localSong)
                    }
                }
                return@withContext file.absolutePath
            }
            catch (e:Exception){
                Log.d("savePreviewToFile",e.message.toString() )
                return@withContext null
            }
        }
    }

    private fun deletePreviewFile(filePath:String){
        val file = File(filePath)
        if (file.exists()){
            file.delete()
        }
    }


}