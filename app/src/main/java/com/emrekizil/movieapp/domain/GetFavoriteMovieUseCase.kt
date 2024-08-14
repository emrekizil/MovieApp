package com.emrekizil.movieapp.domain

import com.emrekizil.movieapp.data.repository.model.MovieDetail
import com.emrekizil.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMovieUseCase @Inject constructor(
    private val repository: MovieRepository
)  {
    operator fun invoke(): Flow<List<MovieDetail>> = repository.getFavoriteMovies()
}