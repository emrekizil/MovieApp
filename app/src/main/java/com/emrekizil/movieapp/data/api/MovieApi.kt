package com.emrekizil.movieapp.data.api

import com.emrekizil.movieapp.data.dto.popular.MovieResponse
import retrofit2.Response
import com.emrekizil.movieapp.BuildConfig.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieApi {

    @Headers(
        "Authorization : ${API_KEY}"
    )
    @GET("/3/movie/popular")
    suspend fun getPopularMovie(@Query("page") pageNumber: Int): Response<MovieResponse>
}