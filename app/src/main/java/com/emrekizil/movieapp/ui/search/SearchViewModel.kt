package com.emrekizil.movieapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.emrekizil.movieapp.domain.GetFavoriteMovieUseCase
import com.emrekizil.movieapp.domain.GetMovieByNameUseCase
import com.emrekizil.movieapp.ui.component.BaseMovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getMovieByNameUseCase: GetMovieByNameUseCase,
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase
) : ViewModel() {
    private val _searchUiState = MutableStateFlow<PagingData<BaseMovieUiState>>(PagingData.empty())
    val searchUiState: StateFlow<PagingData<BaseMovieUiState>> get() = _searchUiState

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
                        posterPath = movie.posterPath
                    )
                }
                _searchUiState.update {
                    newMovies
                }
            }.collect()
        }
    }
}