package com.emrekizil.core_domain


import androidx.paging.PagingData
import com.emrekizil.core_domain.repository.MovieRepository
import com.emrekizil.core_model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieByNameUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(pageNumber: Int, query: String): Flow<PagingData<Movie>> =
        repository.getMovieByName(pageNumber, query)
}