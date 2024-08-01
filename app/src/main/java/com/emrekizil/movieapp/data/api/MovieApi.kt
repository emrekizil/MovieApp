package com.emrekizil.movieapp.data.api

import com.emrekizil.movieapp.BuildConfig
import com.emrekizil.movieapp.data.dto.popular.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("/3/movie/popular")
    suspend fun getPopularMovie(
        @Query("page") pageNumber: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieResponse>
}