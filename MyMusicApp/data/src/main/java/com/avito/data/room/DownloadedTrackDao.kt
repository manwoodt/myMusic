package com.avito.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadedTrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownloadedTrack(downloadedTrack:DownloadedTrackEntity)

    @Query("SELECT * from downloaded_tracks")
    fun getAllDownloadedTracks(): Flow<List<DownloadedTrackEntity>>

    @Query("DELETE FROM downloaded_tracks WHERE id = :trackId")
    suspend fun deleteDownloadedTrack(trackId:Long)

    @Query("SELECT * FROM downloaded_tracks WHERE title LIKE :query OR artist LIKE :query")
    suspend fun searchDownloadedTracks(query: String): List<DownloadedTrackEntity>

}