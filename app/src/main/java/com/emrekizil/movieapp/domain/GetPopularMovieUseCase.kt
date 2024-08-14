package com.emrekizil.movieapp.domain

import androidx.paging.PagingData
import com.emrekizil.movieapp.data.repository.model.Movie
import com.emrekizil.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke (pageNumber: Int): Flow<PagingData<Movie>> = repository.getPopularMovie(pageNumber)
}