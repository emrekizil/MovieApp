package com.emrekizil.core_domain

import com.emrekizil.core_domain.repository.MovieRepository
import com.emrekizil.core_model.ResponseState
import com.emrekizil.core_model.dto.detail.MovieDetailResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailByIdUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<ResponseState<MovieDetailResponse>> =
        repository.getMovieDetailById(movieId)
}