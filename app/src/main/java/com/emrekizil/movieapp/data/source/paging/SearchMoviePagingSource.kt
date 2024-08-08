package com.emrekizil.movieapp.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.emrekizil.movieapp.data.repository.Movie
import com.emrekizil.movieapp.data.repository.toMovie
import com.emrekizil.movieapp.data.source.remote.RemoteDataSource


class SearchMoviePagingSource(
    private val remoteDataSource: RemoteDataSource, private val query: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = remoteDataSource.getMovieByName(page,query)
            val result = response.body()?.results ?: emptyList()
            val movie = result.map {
                it!!.toMovie()
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

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}