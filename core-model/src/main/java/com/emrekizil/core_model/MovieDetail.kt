package com.emrekizil.core_model

import com.emrekizil.core_model.entity.FavoriteMovieEntity


data class MovieDetail(
    val id: Int,
    val overview: String,
    val backdropPath: String,
    val genres: List<String>,
    val voteAverage: Double,
    val title: String,
    val releaseDate: String
) {
    fun toEntity() : FavoriteMovieEntity {
        return FavoriteMovieEntity(
            this.id,
            this.overview,
            this.backdropPath,
            this.genres,
            this.voteAverage,
            this.title,
            this.releaseDate
        )
    }
}
