package com.emrekizil.core_domain

import com.emrekizil.core_model.Movie
import com.emrekizil.core_model.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSimilarMovieByIdUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(movieId: Int): Flow<ResponseState<List<Movie>>> =
        repository.getSimilarMovieById(movieId)
}