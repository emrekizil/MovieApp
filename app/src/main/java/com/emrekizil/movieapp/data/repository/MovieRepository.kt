package com.emrekizil.movieapp.data.repository

import androidx.paging.PagingData
import com.emrekizil.movieapp.data.ResponseState
import com.emrekizil.movieapp.data.dto.detail.MovieDetailResponse
import com.emrekizil.movieapp.data.repository.model.Movie
import com.emrekizil.movieapp.data.repository.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovie(pageNumber: Int): Flow<PagingData<Movie>>
    fun getMovieByName(pageNumber: Int, query:String) : Flow<PagingData<Movie>>
    fun getMovieDetailById(movieId:Int):Flow<ResponseState<MovieDetailResponse>>
    suspend fun insertMovie(movie: MovieDetail)
    suspend fun deleteMovie(movie: MovieDetail)
    fun getFavoriteMovies(): Flow<List<MovieDetail>>
    fun getSimilarMovieById(movieId: Int) : Flow<ResponseState<List<Movie>>>
    suspend fun saveLayoutPreference(isGridMode: Boolean)
    fun getLayoutPreference(): Flow<Boolean>
}