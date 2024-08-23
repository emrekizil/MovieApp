package com.emrekizil.core_data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.emrekizil.core_data.mapper.toMovieList
import com.emrekizil.core_data.paging.MoviePagingSource
import com.emrekizil.core_data.paging.SearchMoviePagingSource
import com.emrekizil.core_database.local.LocalDataSource
import com.emrekizil.core_domain.repository.MovieRepository
import com.emrekizil.core_model.Movie
import com.emrekizil.core_model.MovieDetail
import com.emrekizil.core_model.ResponseState
import com.emrekizil.core_model.dto.detail.MovieDetailResponse
import com.emrekizil.core_network.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) :
    MovieRepository {
    override fun getPopularMovie(pageNumber: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(20),
        ) {
            MoviePagingSource(remoteDataSource)
        }
            .flow

    }

    override fun getMovieByName(pageNumber: Int, query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(20),
        ) {
            SearchMoviePagingSource(remoteDataSource, query)
        }
            .flow
    }

    override fun getMovieDetailById(movieId: Int): Flow<ResponseState<MovieDetailResponse>> {
        return flow {
            emit(ResponseState.Loading)
            val response = remoteDataSource.getMovieDetailById(movieId).body()!!
            emit(ResponseState.Success(response))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override suspend fun insertMovie(movie: MovieDetail) {
        localDataSource.insertMovie(movie.toEntity())
    }

    override suspend fun deleteMovie(movie: MovieDetail) {
        localDataSource.deleteMovie(movie.toEntity())
    }

    override fun getFavoriteMovies(): Flow<List<MovieDetail>> =
        localDataSource.getFavoriteMovies().map {
            it.map { entity -> entity.toMovieDetail() }
        }

    override fun getSimilarMovieById(movieId: Int): Flow<ResponseState<List<Movie>>> {
        return flow {
            emit(ResponseState.Loading)
            val response = remoteDataSource.getSimilarMovieById(movieId).toMovieList().take(3)
            emit(ResponseState.Success(response))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override suspend fun saveLayoutPreference(isGridMode: Boolean) {
        localDataSource.saveLayoutPreference(isGridMode)
    }

    override fun getLayoutPreference(): Flow<Boolean> = localDataSource.getLayoutPreference()
}