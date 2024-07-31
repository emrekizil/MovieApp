package com.emrekizil.movieapp.data.repository

import androidx.paging.PagingData
import com.emrekizil.movieapp.data.dto.popular.Result
import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    fun getPopularMovie(pageNumber: Int): Flow<PagingData<Result>>
}