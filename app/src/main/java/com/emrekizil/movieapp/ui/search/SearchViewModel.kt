package com.emrekizil.movieapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.emrekizil.movieapp.data.repository.Movie
import com.emrekizil.movieapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _searchUiState = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val searchUiState: StateFlow<PagingData<Movie>> get() = _searchUiState

    fun getMovieByName(query: String) {
        viewModelScope.launch {
            repository.getMovieByName(1, query)
                .cachedIn(viewModelScope)
                .collectLatest { newMovie ->
                    _searchUiState.value = newMovie
                }
        }
    }
}