package com.emrekizil.movieapp.ui.search

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.emrekizil.movieapp.R
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

    private val viewmodel: SearchViewModel by viewModels()

    private val adapter = SearchPagingAdapter().apply {
        setOnMovieItemClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(it)
            navigate(action)
        }
    }

    override fun observeUi() {
        super.observeUi()
        showBottomNavigationBar()
        binding.searchRecyclerView.adapter = adapter
        getUiState()
        binding.searchEditText.observeTextChanges()
            .filter { it okWith MINIMUM_SEARCH_LENGTH }
            .debounce(MILLISECONDS)
            .distinctUntilChanged()
            .onEach {
                viewmodel.getMovieByName(it)
            }
            .launchIn(lifecycleScope)
        checkDataVisibility()
    }

    private fun getUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.searchUiState.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun checkDataVisibility() {
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                val isListEmpty = adapter.itemCount == 0 &&
                        loadStates.refresh is LoadState.NotLoading &&
                        loadStates.append.endOfPaginationReached

                if (isListEmpty) {
                    showSnackbar(getString(R.string.not_found),null,null)
                }
            }
        }
    }

    companion object {
        private const val MILLISECONDS = 200L
        private const val MINIMUM_SEARCH_LENGTH = 3
    }
}