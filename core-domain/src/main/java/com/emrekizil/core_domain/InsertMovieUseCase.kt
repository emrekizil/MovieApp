package com.emrekizil.core_domain

import com.emrekizil.core_model.MovieDetail
import javax.inject.Inject

class InsertMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: MovieDetail) {
        repository.insertMovie(movie)
    }
}
