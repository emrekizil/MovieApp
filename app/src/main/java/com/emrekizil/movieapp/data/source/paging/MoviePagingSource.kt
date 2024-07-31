package com.emrekizil.movieapp.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.emrekizil.movieapp.data.dto.popular.Result
import com.emrekizil.movieapp.data.source.remote.RemoteDataSource

class MoviePagingSource (private val remoteDataSource: RemoteDataSource) :
    PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val page = params.key ?: 1
            val response = remoteDataSource.getPopularMovie(page)
            val result = response.body()?.results.orEmpty()
            val resultNotEmpty = result.map {
                it!!
            }
            LoadResult.Page(
                data = resultNotEmpty,
                prevKey = null,
                nextKey = if (page == response.body()?.totalPages) null else (page + 1)
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}