package com.emrekizil.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.emrekizil.movieapp.data.ResponseState
import com.emrekizil.movieapp.data.dto.detail.MovieDetailResponse
import com.emrekizil.movieapp.data.dto.popular.Result
import com.emrekizil.movieapp.data.source.paging.MoviePagingSource
import com.emrekizil.movieapp.data.source.paging.SearchMoviePagingSource
import com.emrekizil.movieapp.data.source.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    MovieRepository {
    override fun getPopularMovie(pageNumber: Int): Flow<PagingData<Result>> {
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
}