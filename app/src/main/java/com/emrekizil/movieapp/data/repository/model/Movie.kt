package com.emrekizil.movieapp.data.repository.model


data class Movie(
    val id: Int,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val backdropPath: String
)
