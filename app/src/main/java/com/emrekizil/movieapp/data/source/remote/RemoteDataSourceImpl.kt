package com.emrekizil.movieapp.data.source.remote

import com.emrekizil.movieapp.data.api.MovieApi
import com.emrekizil.movieapp.data.dto.detail.MovieDetailResponse
import com.emrekizil.movieapp.data.dto.popular.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val movieApi: MovieApi) : RemoteDataSource {
    override suspend fun getPopularMovie(pageNumber: Int): Response<MovieResponse> =
        movieApi.getPopularMovie(pageNumber)

    override suspend fun getMovieByName(pageNumber: Int, query: String): Response<MovieResponse> =
        movieApi.getMovieByName(pageNumber, query)

    override suspend fun getMovieDetailById(movieId: Int): Response<MovieDetailResponse> =
        movieApi.getMovieDetailById(movieId)

}