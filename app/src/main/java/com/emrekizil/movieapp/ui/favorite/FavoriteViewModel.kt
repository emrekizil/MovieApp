package com.emrekizil.movieapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emrekizil.movieapp.data.repository.model.MovieDetail
import com.emrekizil.movieapp.domain.DeleteMovieUseCase
import com.emrekizil.movieapp.domain.GetFavoriteMovieUseCase
import com.emrekizil.movieapp.domain.InsertMovieUseCase
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
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase
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

    fun deleteMovie(movieDetail: MovieDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteMovieUseCase(movieDetail)
        }
    }
}