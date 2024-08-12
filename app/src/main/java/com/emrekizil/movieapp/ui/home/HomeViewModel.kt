package com.emrekizil.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.emrekizil.movieapp.data.dto.popular.Result
import com.emrekizil.movieapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {

    private val _homeUiState: Flow<PagingData<Result>> = repository.getPopularMovie(1).cachedIn(viewModelScope)
    val homeUiState = _homeUiState

    private val _layoutPreference = MutableStateFlow(false)
    val layoutPreference = _layoutPreference.asStateFlow()

    init {
        getLayoutPreference()
    }
    fun getLayoutPreference(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLayoutPreference().collect{
                _layoutPreference.update {
                    it
                }
            }
        }
    }

    fun saveLayoutPreference(isGridMode:Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveLayoutPreference(isGridMode)
        }
    }

}