package com.emrekizil.movieapp.data.repository

import com.emrekizil.movieapp.data.dto.popular.Result

fun Result.toMovie() : Movie{
    return Movie (
        id = this.id ?: 0,
        posterPath = this.getPosterImageUrl(),
        title = this.title ?: "",
        voteAverage = this.getRatingRounded()
    )
}