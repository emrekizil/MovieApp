package com.emrekizil.core_domain

import com.emrekizil.core_model.MovieDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMovieUseCase @Inject constructor(
    private val repository: MovieRepository
)  {
    operator fun invoke(): Flow<List<MovieDetail>> = repository.getFavoriteMovies()
}