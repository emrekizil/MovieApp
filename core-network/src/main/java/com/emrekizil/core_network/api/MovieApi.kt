package com.emrekizil.core_network.api


import com.emrekizil.core_network.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("/3/movie/popular")
    suspend fun getPopularMovie(
        @Query("page") pageNumber: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<com.emrekizil.core_model.dto.popular.MovieResponse>

    @GET("/3/search/movie")
    suspend fun getMovieByName(
        @Query("page") pageNumber: Int,
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<com.emrekizil.core_model.dto.popular.MovieResponse>

    @GET("/3/movie/{movieId}")
    suspend fun getMovieDetailById(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : Response<com.emrekizil.core_model.dto.detail.MovieDetailResponse>

    @GET("/3/movie/{movieId}/similar")
    suspend fun getSimilarMovieById(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ) : Response<com.emrekizil.core_model.dto.popular.MovieResponse>
}