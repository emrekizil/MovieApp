package com.emrekizil.core_data.mapper

import com.emrekizil.core_model.Movie
import com.emrekizil.core_model.dto.popular.MovieResponse
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
