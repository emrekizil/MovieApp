package com.emrekizil.core_network.remote

import com.emrekizil.core_model.dto.detail.MovieDetailResponse
import com.emrekizil.core_model.dto.popular.MovieResponse
import com.emrekizil.core_network.api.MovieApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val movieApi: MovieApi) :
    RemoteDataSource {
    override suspend fun getPopularMovie(pageNumber: Int): Response<MovieResponse> =
        movieApi.getPopularMovie(pageNumber)

    override suspend fun getMovieByName(pageNumber: Int, query: String): Response<MovieResponse> =
        movieApi.getMovieByName(pageNumber, query)

    override suspend fun getMovieDetailById(movieId: Int): Response<MovieDetailResponse> =
        movieApi.getMovieDetailById(movieId)

    override suspend fun getSimilarMovieById(movieId: Int): Response<MovieResponse> =
        movieApi.getSimilarMovieById(movieId)

}