package com.emrekizil.movieapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.emrekizil.movieapp.data.repository.Movie
import com.emrekizil.movieapp.databinding.FragmentSearchBinding
import com.emrekizil.movieapp.ui.base.BaseFragment
import com.emrekizil.movieapp.utils.observeTextChanges
import com.emrekizil.movieapp.utils.okWith
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewmodel : SearchViewModel by viewModels()

    private val adapter  = SearchPagingAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchRecyclerView.adapter = adapter
        getData()
        binding.searchEditText.observeTextChanges()
            .filter { it okWith MINIMUM_SEARCH_LENGTH  }
            .debounce(MILLISECONDS)
            .distinctUntilChanged()
            .onEach{
                viewmodel.getMovieByName(it)
            }
            .launchIn(lifecycleScope)
    }

    private fun getData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewmodel.searchUiState.collectLatest{
                    adapter.submitData(it)
                }
            }
        }
    }

    companion object{
        private const val MILLISECONDS = 200L
        private const val MINIMUM_SEARCH_LENGTH = 3
    }
}