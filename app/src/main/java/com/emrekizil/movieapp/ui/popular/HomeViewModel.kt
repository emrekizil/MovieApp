package com.emrekizil.movieapp.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.emrekizil.movieapp.data.dto.popular.Result
import com.emrekizil.movieapp.data.repository.MovieRepository
import com.emrekizil.movieapp.data.source.remote.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _homeUiState : MutableStateFlow<PagingData<Result>?> = MutableStateFlow(null)
    private val homeUiState = _homeUiState.asStateFlow()


    fun getPopularMovie(pageNumber: Int) {
        viewModelScope.launch {
            repository.getPopularMovie(pageNumber).collect {
                _homeUiState.update {
                    it
                }
            }
        }
    }

    fun yes(){
        getPopularMovie(1)
    }

}