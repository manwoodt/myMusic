package com.avito.mymusic

import android.app.Application
import com.avito.data.dataModule
import com.avito.domain.domainModule
import com.avito.presentation.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MyApplication)
            modules(dataModule, domainModule,presentationModule)
        }
    }
}