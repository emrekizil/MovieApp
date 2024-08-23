package com.emrekizil.core_database.local

import com.emrekizil.core_model.entity.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertMovie(movie: FavoriteMovieEntity)
    suspend fun deleteMovie(movie: FavoriteMovieEntity)
    fun getFavoriteMovies(): Flow<List<FavoriteMovieEntity>>
    suspend fun saveLayoutPreference(isGridMode: Boolean)
    fun getLayoutPreference(): Flow<Boolean>
}