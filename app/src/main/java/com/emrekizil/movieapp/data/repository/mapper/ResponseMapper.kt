package com.emrekizil.movieapp.data.repository.mapper

import com.emrekizil.movieapp.data.dto.popular.MovieResponse
import com.emrekizil.movieapp.data.repository.model.Movie
import retrofit2.Response

fun Response<MovieResponse>.toMovieList():List<Movie> {
    return body()!!.results!!.map {
        Movie(
            it?.id ?: 0,
            it?.getPosterImageUrl() ?: "",
            it?.title.orEmpty(),
            it?.getRatingRounded() ?: 0.0,
            it?.getBackdropImageUrl() ?: ""
        )
    }
}
