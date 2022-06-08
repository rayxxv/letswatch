package com.rayxxv.letswatch.di

import com.rayxxv.letswatch.data.ApiService
import com.rayxxv.letswatch.data.ApiHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val BASE_URL = "https://api.themoviedb.org/3/"
val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val url = original.url.newBuilder()
                    .addQueryParameter("api_key", "3dd2e30d36922558aeaa226cce93793d")
                    .build()

                val request = original.newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single {
        get<Retrofit>().create(ApiService::class.java)
    }

    singleOf(::ApiHelper)

//    single{
//        ApiHelper(get())
//    }
}