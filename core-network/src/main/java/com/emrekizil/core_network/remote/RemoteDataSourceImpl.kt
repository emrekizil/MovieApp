package com.emrekizil.core_network.remote

import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val movieApi: com.emrekizil.core_network.api.MovieApi) :
    RemoteDataSource {
    override suspend fun getPopularMovie(pageNumber: Int): Response<com.emrekizil.core_model.dto.popular.MovieResponse> =
        movieApi.getPopularMovie(pageNumber)

    override suspend fun getMovieByName(pageNumber: Int, query: String): Response<com.emrekizil.core_model.dto.popular.MovieResponse> =
        movieApi.getMovieByName(pageNumber, query)

    override suspend fun getMovieDetailById(movieId: Int): Response<com.emrekizil.core_model.dto.detail.MovieDetailResponse> =
        movieApi.getMovieDetailById(movieId)

    override suspend fun getSimilarMovieById(movieId: Int): Response<com.emrekizil.core_model.dto.popular.MovieResponse> =
        movieApi.getSimilarMovieById(movieId)

}