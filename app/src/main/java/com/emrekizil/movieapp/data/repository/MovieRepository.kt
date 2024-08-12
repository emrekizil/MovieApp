package com.emrekizil.movieapp.data.repository

import androidx.paging.PagingData
import com.emrekizil.movieapp.data.ResponseState
import com.emrekizil.movieapp.data.dto.detail.MovieDetailResponse
import com.emrekizil.movieapp.data.dto.popular.Result
import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    fun getPopularMovie(pageNumber: Int): Flow<PagingData<Result>>
    fun getMovieByName(pageNumber: Int, query:String) : Flow<PagingData<Movie>>
    fun getMovieDetailById(movieId:Int):Flow<ResponseState<MovieDetailResponse>>
    suspend fun insertMovie(movie: MovieDetail)
    suspend fun deleteMovie(movie: MovieDetail)
    fun getFavoriteMovies(): Flow<List<MovieDetail>>
    suspend fun saveLayoutPreference(isGridMode: Boolean)
    fun getLayoutPreference(): Flow<Boolean>
}