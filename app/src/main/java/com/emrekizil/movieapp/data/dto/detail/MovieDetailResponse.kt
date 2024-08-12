package com.emrekizil.movieapp.data.dto.detail


import com.emrekizil.movieapp.data.repository.MovieDetail
import com.google.gson.annotations.SerializedName
import kotlin.math.round

data class MovieDetailResponse(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection?,
    @SerializedName("budget")
    val budget: Int?,
    @SerializedName("genres")
    val genres: List<Genre?>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imdb_id")
    val imdbİd: String?,
    @SerializedName("origin_country")
    val originCountry: List<String?>?,
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
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany?>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry?>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("revenue")
    val revenue: Int?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?,
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
        return "https://image.tmdb.org/t/p/original${this.backdropPath}"
    }

    fun getRatingRounded(): Double {
        return this.voteAverage?.let {
            round(it.times(10)) / 10
        } ?: 0.0
    }

    fun toMovieDetail() : MovieDetail {
        return MovieDetail(
            this.id ?: 0,
            this.overview ?: "",
            this.backdropPath ?: "",
            this.genres?.map {
                it?.name!!
            } ?: listOf(""),
            this.voteAverage ?: 0.0,
            this.title ?: "",
            this.releaseDate ?:""
        )
    }
}