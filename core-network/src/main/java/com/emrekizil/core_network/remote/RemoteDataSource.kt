package com.emrekizil.core_network.remote

import retrofit2.Response

interface RemoteDataSource {
    suspend fun getPopularMovie(pageNumber: Int): Response<com.emrekizil.core_model.dto.popular.MovieResponse>
    suspend fun getMovieByName(pageNumber: Int, query: String): Response<com.emrekizil.core_model.dto.popular.MovieResponse>
    suspend fun getMovieDetailById(movieId: Int): Response<com.emrekizil.core_model.dto.detail.MovieDetailResponse>
    suspend fun getSimilarMovieById(movieId: Int): Response<com.emrekizil.core_model.dto.popular.MovieResponse>
}