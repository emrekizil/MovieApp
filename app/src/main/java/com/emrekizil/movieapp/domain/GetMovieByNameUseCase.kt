package com.emrekizil.movieapp.domain

import androidx.paging.PagingData
import com.emrekizil.movieapp.data.repository.model.Movie
import com.emrekizil.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieByNameUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(pageNumber: Int, query: String): Flow<PagingData<Movie>> =
        repository.getMovieByName(pageNumber, query)
}