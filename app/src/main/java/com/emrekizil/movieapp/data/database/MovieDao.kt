package com.emrekizil.movieapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert
    suspend fun insertMovie(movie: FavoriteMovieEntity)

    @Delete
    suspend fun deleteMovie(movie: FavoriteMovieEntity)

    @Query("SELECT * from favoritemovieentity ORDER BY id ASC" )
    fun getFavoriteMovies():Flow<List<FavoriteMovieEntity>>
}