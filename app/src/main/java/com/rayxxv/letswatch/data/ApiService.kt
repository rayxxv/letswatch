package com.rayxxv.letswatch.data

import com.rayxxv.letswatch.data.pojo.Movie
import com.rayxxv.letswatch.data.pojo.PopularMoviesResponse
import com.rayxxv.letswatch.data.pojo.ResultX
import com.rayxxv.letswatch.data.pojo.Tv
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/popular")
    suspend fun getAllPopularMovies(): PopularMoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id")movieid:Int): Movie

    @GET("tv/popular")
    suspend fun getAllSeries(): Tv

    @GET("tv/{tv_id}")
    suspend fun getSeriesDetail(@Path("tv_id")tvid:Int): ResultX
}