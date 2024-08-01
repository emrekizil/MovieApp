package com.emrekizil.movieapp.data.source.remote

import com.emrekizil.movieapp.data.api.MovieApi
import com.emrekizil.movieapp.data.dto.popular.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val movieApi: MovieApi) : RemoteDataSource {
    override suspend fun getPopularMovie(pageNumber: Int): Response<MovieResponse> =
        movieApi.getPopularMovie(pageNumber)
}