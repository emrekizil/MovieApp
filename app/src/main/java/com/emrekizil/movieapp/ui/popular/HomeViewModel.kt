package com.emrekizil.movieapp.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.emrekizil.movieapp.data.dto.popular.Result
import com.emrekizil.movieapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {
    private val _homeUiState: Flow<PagingData<Result>> = repository.getPopularMovie(1).cachedIn(viewModelScope)
    val homeUiState = _homeUiState

}