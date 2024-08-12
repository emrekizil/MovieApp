package com.emrekizil.movieapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emrekizil.movieapp.data.ResponseState
import com.emrekizil.movieapp.data.dto.detail.MovieDetailResponse
import com.emrekizil.movieapp.data.repository.MovieDetail
import com.emrekizil.movieapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _detailUiState: MutableStateFlow<MovieDetailScreenUiState> =
        MutableStateFlow(MovieDetailScreenUiState.Loading)
    val detailUiState: StateFlow<MovieDetailScreenUiState> = _detailUiState.asStateFlow()


    fun getMovie(movieId: Int) {
        viewModelScope.launch {
            combine(
                repository.getMovieDetailById(movieId),
                repository.getFavoriteMovies()
            ) { movieResponseState, favoriteMovies ->
                when (movieResponseState) {
                    is ResponseState.Error -> {
                        _detailUiState.update {
                            MovieDetailScreenUiState.Error(movieResponseState.message)
                        }
                    }

                    ResponseState.Loading -> {
                        _detailUiState.update {
                            MovieDetailScreenUiState.Loading
                        }
                    }

                    is ResponseState.Success -> {
                        val isFavorite = favoriteMovies.any { it.id == movieResponseState.data.id }
                        _detailUiState.update {
                            MovieDetailScreenUiState.Success(
                                MovieUiState(
                                    id = movieResponseState.data.id!!,
                                    overview = movieResponseState.data.overview!!,
                                    backdropPath = movieResponseState.data.getPosterImageUrl(),
                                    genres = movieResponseState.data.genres?.map {
                                        it?.name!!
                                    } ?: listOf(""),
                                    voteAverage = movieResponseState.data.getRatingRounded(),
                                    title = movieResponseState.data.title!!,
                                    releaseDate = movieResponseState.data.releaseDate!!,
                                    isFavorite = isFavorite,
                                    onFavorite = {
                                        changeFavoriteMovieState(
                                            movieResponseState.data,
                                            isFavorite
                                        )
                                    }
                                )
                            )
                        }
                    }
                }
            }.collect()
        }
    }

    private fun changeFavoriteMovieState(data: MovieDetailResponse, favorite: Boolean) {
        viewModelScope.launch {
            if (favorite){
                repository.deleteMovie(data.toMovieDetail())
            } else {
                repository.insertMovie(data.toMovieDetail())
            }
        }
    }
}

sealed class MovieDetailScreenUiState {
    data object Loading : MovieDetailScreenUiState()
    data class Error(val message: String) : MovieDetailScreenUiState()
    data class Success(val data: MovieUiState) : MovieDetailScreenUiState()
}

data class MovieUiState(
    val id: Int,
    val overview: String,
    val backdropPath: String,
    val genres: List<String>,
    val voteAverage: Double,
    val title: String,
    val releaseDate: String,
    val isFavorite: Boolean,
    val onFavorite: () -> Unit
) {
    fun toMovieDetail() : MovieDetail {
        return MovieDetail(
            this.id ,
            this.overview ,
            this.backdropPath ,
            this.genres,
            this.voteAverage ,
            this.title,
            this.releaseDate
        )
    }
}