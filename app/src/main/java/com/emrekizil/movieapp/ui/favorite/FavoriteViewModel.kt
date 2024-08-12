package com.emrekizil.movieapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emrekizil.movieapp.data.repository.MovieDetail
import com.emrekizil.movieapp.data.repository.MovieRepository
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
    private val repository: MovieRepository
) : ViewModel() {

    private val _favoriteUiState = MutableStateFlow(emptyList<MovieDetail>())
    val favoriteUiState = _favoriteUiState.asStateFlow()


    fun getFavoriteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavoriteMovies().collectLatest { favoriteMovies->
                _favoriteUiState.update {
                    favoriteMovies
                }
            }
        }
    }

}