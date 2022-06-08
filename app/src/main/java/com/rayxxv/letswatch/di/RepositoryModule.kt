package com.rayxxv.letswatch.di
import com.rayxxv.letswatch.data.Repository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val RepositoryModule = module{
    singleOf(::Repository)
}