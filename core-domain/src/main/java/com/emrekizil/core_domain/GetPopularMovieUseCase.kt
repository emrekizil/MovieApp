package com.emrekizil.core_domain


import androidx.paging.PagingData
import com.emrekizil.core_domain.repository.MovieRepository
import com.emrekizil.core_model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke (pageNumber: Int): Flow<PagingData<Movie>> = repository.getPopularMovie(pageNumber)
}