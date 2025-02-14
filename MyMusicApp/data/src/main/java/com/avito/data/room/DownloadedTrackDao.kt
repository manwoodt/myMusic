package com.avito.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadedTrackDao {

    @Insert
    suspend fun insertDownloadedTrack(downloadedTrack:DownloadedTrackEntity)

    @Query("SELECT * from downloaded_tracks")
    fun getAllDownloadedTracks(): Flow<List<DownloadedTrackEntity>>

    @Query("DELETE FROM downloaded_tracks WHERE id = :trackId")
    suspend fun deleteDownloadedTrack(trackId:Long)

}