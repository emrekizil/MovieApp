package com.emrekizil.core_data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.emrekizil.core_data.mapper.mapTo
import com.emrekizil.core_data.mapper.toMovieList

class MoviePagingSource (private val remoteDataSource: com.emrekizil.core_network.remote.RemoteDataSource) :
    PagingSource<Int, com.emrekizil.core_model.Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.emrekizil.core_model.Movie> {
        return try {
            val page = params.key ?: 1
            val response = remoteDataSource.getPopularMovie(page)
            val movie = response.mapTo {
                it.toMovieList()
            }
            LoadResult.Page(
                data = movie,
                prevKey = null,
                nextKey = if (page == response.body()?.totalPages) null else (page + 1)
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, com.emrekizil.core_model.Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}