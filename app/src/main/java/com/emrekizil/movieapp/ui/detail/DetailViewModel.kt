package com.emrekizil.movieapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emrekizil.movieapp.data.ResponseState
import com.emrekizil.movieapp.data.dto.detail.MovieDetailResponse
import com.emrekizil.movieapp.data.repository.model.Movie
import com.emrekizil.movieapp.data.repository.model.MovieDetail
import com.emrekizil.movieapp.domain.DeleteMovieUseCase
import com.emrekizil.movieapp.domain.GetFavoriteMovieUseCase
import com.emrekizil.movieapp.domain.GetMovieDetailByIdUseCase
import com.emrekizil.movieapp.domain.GetSimilarMovieByIdUseCase
import com.emrekizil.movieapp.domain.InsertMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSimilarMovieByIdUseCase: GetSimilarMovieByIdUseCase,
    private val getMovieDetailByIdUseCase: GetMovieDetailByIdUseCase,
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase
) : ViewModel() {
    private val _detailUiState: MutableStateFlow<MovieDetailScreenUiState> =
        MutableStateFlow(MovieDetailScreenUiState.Loading)
    val detailUiState: StateFlow<MovieDetailScreenUiState> = _detailUiState.asStateFlow()

    private val _similarMovieUiState : MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val similarMovieUiState = _similarMovieUiState.asStateFlow()


    fun getMovie(movieId: Int) {
        viewModelScope.launch {
            combine(
                getMovieDetailByIdUseCase(movieId),
                getFavoriteMovieUseCase()
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
    fun getSimilarMovie(movieId: Int) {
        viewModelScope.launch {
            getSimilarMovieByIdUseCase(movieId).collectLatest { response->
                when (response) {
                    is ResponseState.Success -> {
                        _similarMovieUiState.update {
                            response.data
                        }
                    }
                    is ResponseState.Error -> {}
                    is ResponseState.Loading -> {}
                }
            }
        }
    }
    private fun changeFavoriteMovieState(data: MovieDetailResponse, favorite: Boolean) {
        viewModelScope.launch {
            if (favorite){
                deleteMovieUseCase(data.toMovieDetail())
            } else {
                insertMovieUseCase(data.toMovieDetail())
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
)