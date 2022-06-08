package com.rayxxv.letswatch

import android.app.Application
import com.rayxxv.letswatch.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RetrofitKoin: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()

            androidContext(this@RetrofitKoin)

            modules(
                listOf(networkModule, RepositoryModule, viewModelModule, dataStoreModule, databaseModule)
            )
        }
    }
}