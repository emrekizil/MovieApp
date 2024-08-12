package com.emrekizil.movieapp.data.source.local

import com.emrekizil.movieapp.data.database.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertMovie(movie: FavoriteMovieEntity)

    suspend fun deleteMovie(movie: FavoriteMovieEntity)

    fun getFavoriteMovies(): Flow<List<FavoriteMovieEntity>>

    suspend fun saveLayoutPreference(isGridMode: Boolean)
    fun getLayoutPreference(): Flow<Boolean>
}