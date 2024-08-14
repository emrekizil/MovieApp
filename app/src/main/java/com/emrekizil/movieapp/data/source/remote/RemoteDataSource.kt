package com.emrekizil.movieapp.data.source.remote

import com.emrekizil.movieapp.data.dto.detail.MovieDetailResponse
import com.emrekizil.movieapp.data.dto.popular.MovieResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getPopularMovie(pageNumber: Int): Response<MovieResponse>
    suspend fun getMovieByName(pageNumber: Int, query: String): Response<MovieResponse>
    suspend fun getMovieDetailById(movieId: Int): Response<MovieDetailResponse>
    suspend fun getSimilarMovieById(movieId: Int): Response<MovieResponse>
}