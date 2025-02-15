package com.avito.data

import android.content.Context
import androidx.room.Room
import com.avito.data.api.DeezerApiService
import com.avito.data.repositoryImpl.ApiTracksRepositoryImpl
import com.avito.data.repositoryImpl.DownloadedTracksRepositoryImpl
import com.avito.data.room.DownloadedTrackDao
import com.avito.data.room.DownloadedTracksDatabase
import com.avito.domain.repository.ApiTracksRepository
import com.avito.domain.repository.DownloadedTracksRepository
import org.koin.android.ext.koin.androidContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.dsl.module

private const val BASE_URL = "https://api.deezer.com/"

val dataModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeezerApiService::class.java)
    }

    single {
        Room.databaseBuilder(
            get(),
            DownloadedTracksDatabase::class.java,
            "downloaded_tracks_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single<DownloadedTrackDao> { get<DownloadedTracksDatabase>().downloadedTracksDao() }
    single<ApiTracksRepository> { ApiTracksRepositoryImpl(get()) }
    single<DownloadedTracksRepository> {DownloadedTracksRepositoryImpl(get(),get()) }
}
