package com.rayxxv.letswatch.di

import org.koin.dsl.module
import com.rayxxv.letswatch.data.local.DataStoreManager
import org.koin.core.module.dsl.singleOf

val dataStoreModule = module {
    singleOf(::DataStoreManager)
}