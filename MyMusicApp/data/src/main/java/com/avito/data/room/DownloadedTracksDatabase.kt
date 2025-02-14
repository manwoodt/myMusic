package com.avito.data.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [DownloadedTrackEntity::class], version = 1)
abstract class DownloadedTracksDatabase: RoomDatabase() {
    abstract fun downloadedTracksDao(): DownloadedTrackDao
}