package com.emrekizil.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.emrekizil.movieapp.data.dto.popular.Result
import com.emrekizil.movieapp.data.repository.Movie
import com.emrekizil.movieapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<PagingData<Result>>(PagingData.empty())
    val homeUiState: StateFlow<PagingData<Result>> get() = _homeUiState

    private val _searchUiState = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val searchUiState: StateFlow<PagingData<Movie>> get() = _searchUiState

    private val _layoutPreference = MutableStateFlow(false)
    val layoutPreference = _layoutPreference.asStateFlow()

    init {
        getLayoutPreference()
        getPopularMovie()
    }

    fun getLayoutPreference() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLayoutPreference().collect { value ->
                _layoutPreference.update {
                    value
                }
            }
        }
    }

    fun saveLayoutPreference(isGridMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveLayoutPreference(isGridMode)
        }
    }


    fun getMovieByName(query: String) {
        viewModelScope.launch {
            repository.getMovieByName(1, query)
                .cachedIn(viewModelScope)
                .collectLatest { newMovie ->
                    _searchUiState.value = newMovie
                }
        }
    }

    private fun getPopularMovie() {
        viewModelScope.launch {
            repository.getPopularMovie(1)
                .cachedIn(viewModelScope)
                .collectLatest { popularMovie ->
                    _homeUiState.update {
                        popularMovie
                    }
                }
        }
    }

}

data class MovieUiState(
    val id: Int,
    val backdropPath: String,
    val voteAverage: Double,
    val title: String,
    val isFavorite: Boolean,
)