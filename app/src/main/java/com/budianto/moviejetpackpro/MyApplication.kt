package com.budianto.moviejetpackpro

import android.app.Application
import com.budianto.moviejetpackpro.core.di.databaseModule
import com.budianto.moviejetpackpro.core.di.networkModule
import com.budianto.moviejetpackpro.core.di.repositoryModule
import com.budianto.moviejetpackpro.di.useCaseModule
import com.budianto.moviejetpackpro.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                    listOf(
                            databaseModule,
                            networkModule,
                            repositoryModule,
                            useCaseModule,
                            viewModelModule
                    )
            )
        }
    }
}