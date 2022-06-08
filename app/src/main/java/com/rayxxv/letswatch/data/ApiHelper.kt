package com.rayxxv.letswatch.data

class ApiHelper(private val apiService: ApiService) {
    suspend fun getPopularMovies() = apiService.getAllPopularMovies()
    suspend fun getMovieDetail(movieId : Int) = apiService.getMovieDetail(movieId)
    suspend fun getAllSeries() = apiService.getAllSeries()
    suspend fun getSeriesDetail(seriesId: Int) = apiService.getSeriesDetail(seriesId)
}