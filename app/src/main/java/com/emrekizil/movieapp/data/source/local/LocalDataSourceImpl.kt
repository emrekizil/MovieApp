package com.emrekizil.movieapp.data.source.local

import com.emrekizil.movieapp.data.database.entity.FavoriteMovieEntity
import com.emrekizil.movieapp.data.database.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val layoutPreferenceSource:
    LayoutPreferenceSource
) : LocalDataSource {
    override suspend fun insertMovie(movie: FavoriteMovieEntity) {
        movieDao.insertMovie(movie)
    }

    override suspend fun deleteMovie(movie: FavoriteMovieEntity) {
        movieDao.deleteMovie(movie)
    }

    override fun getFavoriteMovies(): Flow<List<FavoriteMovieEntity>> = movieDao.getFavoriteMovies()

    override suspend fun saveLayoutPreference(isGridMode: Boolean) {
        layoutPreferenceSource.saveLayoutPreference(isGridMode)
    }

    override fun getLayoutPreference(): Flow<Boolean> = layoutPreferenceSource.getLayoutPreference()
}