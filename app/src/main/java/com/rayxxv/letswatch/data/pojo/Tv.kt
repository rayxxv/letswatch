package com.rayxxv.letswatch.data.pojo


import com.google.gson.annotations.SerializedName

data class Tv(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultX>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

data class ResultX(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("first_air_date")
    val releaseDate: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("id")
    val number: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)