package com.emrekizil.movieapp.data.api

import com.emrekizil.movieapp.data.dto.popular.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {

    @GET("/3/movie/popular")
    suspend fun getPopularMovie() : Response<MovieResponse>
}