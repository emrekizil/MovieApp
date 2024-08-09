package com.emrekizil.movieapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emrekizil.movieapp.data.ResponseState
import com.emrekizil.movieapp.data.dto.detail.MovieDetailResponse
import com.emrekizil.movieapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _detailUiState: MutableStateFlow<MovieUiState> =
        MutableStateFlow(MovieUiState.Loading)
    val detailUiState: StateFlow<MovieUiState> = _detailUiState.asStateFlow()


    fun getMovie(movieId: Int) {
        viewModelScope.launch {
            repository.getMovieDetailById(movieId).collectLatest { movie ->
                when (movie) {
                    is ResponseState.Error -> {

                    }
                    ResponseState.Loading -> {

                    }
                    is ResponseState.Success -> {
                        _detailUiState.update {
                            MovieUiState.Success(movie.data)
                        }
                    }
                }
            }
        }
    }
}

sealed class MovieUiState {
    data object Loading : MovieUiState()
    data class Error(val message: String) : MovieUiState()
    data class Success(val data: MovieDetailResponse) : MovieUiState()
}