package com.emrekizil.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.emrekizil.movieapp.data.repository.MovieRepository
import com.emrekizil.movieapp.domain.GetFavoriteMovieUseCase
import com.emrekizil.movieapp.domain.GetMovieByNameUseCase
import com.emrekizil.movieapp.domain.GetPopularMovieUseCase
import com.emrekizil.movieapp.ui.component.BaseMovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val getPopularMovieUseCase: GetPopularMovieUseCase,
    private val getMovieByNameUseCase: GetMovieByNameUseCase,
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<PagingData<BaseMovieUiState>>(PagingData.empty())
    val homeUiState: StateFlow<PagingData<BaseMovieUiState>> get() = _homeUiState

    private val _searchUiState = MutableStateFlow<PagingData<BaseMovieUiState>>(PagingData.empty())
    val searchUiState: StateFlow<PagingData<BaseMovieUiState>> get() = _searchUiState

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
            combine(
                getMovieByNameUseCase(1, query).cachedIn(viewModelScope),
                getFavoriteMovieUseCase()
            ) { searchMovies, favoriteMovies ->
                val isFavorite = favoriteMovies.map { it.id }
                val newMovies = searchMovies.map { movie ->
                    BaseMovieUiState(
                        id = movie.id,
                        backdropPath = movie.backdropPath,
                        voteAverage = movie.voteAverage,
                        title = movie.title,
                        isFavorite = isFavorite.any { movie.id == it },
                        movie.posterPath
                    )
                }
                _searchUiState.update {
                    newMovies
                }
            }.collect()
        }
    }

    fun getPopularMovie() {
        viewModelScope.launch {
            combine(
                getPopularMovieUseCase(1).cachedIn(viewModelScope),
                getFavoriteMovieUseCase()
            ){ popularMovies, favoriteMovies ->
                val isFavorite = favoriteMovies.map { it.id }
                val newMovies = popularMovies.map { movie->
                    BaseMovieUiState(
                        id = movie.id,
                        backdropPath = movie.backdropPath,
                        voteAverage = movie.voteAverage,
                        title = movie.title,
                        isFavorite = isFavorite.any { movie.id == it },
                        posterPath = movie.posterPath
                    )
                }
                _homeUiState.update {
                    newMovies
                }
            }.collect()
        }
    }

}
