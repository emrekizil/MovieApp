package com.emrekizil.feature_favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emrekizil.core_model.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteMovieUseCase: com.emrekizil.core_domain.GetFavoriteMovieUseCase,
    private val insertMovieUseCase: com.emrekizil.core_domain.InsertMovieUseCase,
    private val deleteMovieUseCase: com.emrekizil.core_domain.DeleteMovieUseCase
) : ViewModel() {

    private val _favoriteUiState = MutableStateFlow(emptyList<MovieDetail>())
    val favoriteUiState = _favoriteUiState.asStateFlow()

    fun getFavoriteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteMovieUseCase().collectLatest { favoriteMovies ->
                _favoriteUiState.update {
                    favoriteMovies
                }
            }
        }
    }
    fun insertMovie(movieDetail: MovieDetail){
        viewModelScope.launch(Dispatchers.IO) {
            insertMovieUseCase(movieDetail)
        }
    }

    fun deleteMovie(movieDetail:MovieDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteMovieUseCase(movieDetail)
        }
    }
}