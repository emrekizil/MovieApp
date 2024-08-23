package com.emrekizil.core_ui.component

data class BaseMovieUiState(
    val id: Int,
    val backdropPath: String,
    val voteAverage: Double,
    val title: String,
    val isFavorite: Boolean,
    val posterPath:String
)
