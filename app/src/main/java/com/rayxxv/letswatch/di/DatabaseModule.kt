package com.rayxxv.letswatch.di

import androidx.room.Room
import com.rayxxv.letswatch.data.local.UserDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.rayxxv.letswatch.data.local.DatabaseHelper

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext().applicationContext,
            UserDatabase::class.java,
            "UserDatabase"
        ).fallbackToDestructiveMigration().build()
    }
    single {
        get<UserDatabase>().favoriteDao()
    }
    single {
        get<UserDatabase>().userDao()
    }
    singleOf(::DatabaseHelper)
}