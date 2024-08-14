package com.emrekizil.movieapp.domain

import com.emrekizil.movieapp.data.ResponseState
import com.emrekizil.movieapp.data.repository.model.Movie
import com.emrekizil.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSimilarMovieByIdUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(movieId: Int): Flow<ResponseState<List<Movie>>> =
        repository.getSimilarMovieById(movieId)
}