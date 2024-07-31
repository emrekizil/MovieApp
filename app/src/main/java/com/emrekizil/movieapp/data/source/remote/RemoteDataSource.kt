package com.emrekizil.movieapp.data.source.remote

import com.emrekizil.movieapp.data.dto.popular.MovieResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getPopularMovie(pageNumber:Int) : Response<MovieResponse>
}