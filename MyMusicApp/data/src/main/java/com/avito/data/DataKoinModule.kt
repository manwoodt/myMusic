package com.avito.data

import com.avito.data.api.DeezerApiService
import com.avito.data.repositoryImpl.TracksRepositoryImpl
import com.avito.domain.repository.TracksRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.dsl.module

val dataModule = module {
single {
    Retrofit.Builder()
        .baseUrl("https://api.deezer.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DeezerApiService::class.java)
}

    single<TracksRepository> { TracksRepositoryImpl(get()) }
}