package com.avito.data

import com.avito.data.api.DeezerApiService
import com.avito.data.repositoryImpl.TracksRepositoryImpl
import com.avito.domain.repository.TracksRepository
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

    single<TracksRepository> { TracksRepositoryImpl(get()) }
}