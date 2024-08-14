package com.emrekizil.movieapp.domain

import com.emrekizil.movieapp.data.repository.model.MovieDetail
import com.emrekizil.movieapp.data.repository.MovieRepository
import javax.inject.Inject

class InsertMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: MovieDetail) {
        repository.insertMovie(movie)
    }
}
