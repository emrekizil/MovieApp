package com.emrekizil.core_domain.repository

import androidx.paging.PagingData
import com.emrekizil.core_model.Movie
import com.emrekizil.core_model.MovieDetail
import com.emrekizil.core_model.ResponseState
import com.emrekizil.core_model.dto.detail.MovieDetailResponse
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