package com.emrekizil.movieapp.data.dto.popular


import com.google.gson.annotations.SerializedName
import kotlin.math.round

data class Result(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreİds: List<Int?>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {
    fun getPosterImageUrl(): String {
        return "https://image.tmdb.org/t/p/w400${this.posterPath}"
    }

    fun getRatingRounded(): Double {
        return this.voteAverage?.let {
            round(it.times(10)) / 10
        } ?: 0.0
    }

    fun getBackdropImageUrl(): String {
        return "https://image.tmdb.org/t/p/w400/${this.backdropPath}"
    }
}