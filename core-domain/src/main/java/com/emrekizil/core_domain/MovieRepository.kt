package com.emrekizil.core_domain

import androidx.paging.PagingData
import com.emrekizil.core_model.dto.detail.MovieDetailResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovie(pageNumber: Int): Flow<PagingData<com.emrekizil.core_model.Movie>>
    fun getMovieByName(pageNumber: Int, query:String) : Flow<PagingData<com.emrekizil.core_model.Movie>>
    fun getMovieDetailById(movieId:Int):Flow<com.emrekizil.core_model.ResponseState<MovieDetailResponse>>
    suspend fun insertMovie(movie: com.emrekizil.core_model.MovieDetail)
    suspend fun deleteMovie(movie: com.emrekizil.core_model.MovieDetail)
    fun getFavoriteMovies(): Flow<List<com.emrekizil.core_model.MovieDetail>>
    fun getSimilarMovieById(movieId: Int) : Flow<com.emrekizil.core_model.ResponseState<List<com.emrekizil.core_model.Movie>>>
    suspend fun saveLayoutPreference(isGridMode: Boolean)
    fun getLayoutPreference(): Flow<Boolean>
}