package com.emrekizil.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.emrekizil.movieapp.data.dto.popular.Result
import com.emrekizil.movieapp.data.source.paging.MoviePagingSource
import com.emrekizil.movieapp.data.source.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor (private val remoteDataSource: RemoteDataSource) : MovieRepository {
    override fun getPopularMovie(pageNumber: Int): Flow<PagingData<Result>> {
        return Pager(
            config=PagingConfig(20),
        ){
            MoviePagingSource(remoteDataSource)
        }
        .flow

    }
}