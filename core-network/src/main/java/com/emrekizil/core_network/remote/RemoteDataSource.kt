package com.emrekizil.core_network.remote

import com.emrekizil.core_model.dto.detail.MovieDetailResponse
import com.emrekizil.core_model.dto.popular.MovieResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getPopularMovie(pageNumber: Int): Response<MovieResponse>
    suspend fun getMovieByName(pageNumber: Int, query: String): Response<MovieResponse>
    suspend fun getMovieDetailById(movieId: Int): Response<MovieDetailResponse>
    suspend fun getSimilarMovieById(movieId: Int): Response<MovieResponse>
}