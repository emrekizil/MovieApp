package com.emrekizil.movieapp.ui.home

import android.os.Parcelable
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.emrekizil.movieapp.R
import com.emrekizil.movieapp.databinding.FragmentHomeBinding
import com.emrekizil.movieapp.ui.base.BaseFragment
import com.emrekizil.movieapp.ui.search.SearchPagingAdapter
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
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private val adapter = MoviePagingAdapter { movieId ->
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId)
        navigate(action)
    }

    private val searchAdapter = SearchPagingAdapter().apply {
        setOnMovieItemClickListener { movieId ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId)
            navigate(action)
        }
    }

    private var layoutManagerState: Parcelable? = null

    private var isGridMode = false

    override fun observeUi() {
        super.observeUi()
        setUpAdapter()
        getUiState()
        viewModel.getLayoutPreference()
        binding.layoutManagerButton.setOnClickListener {
            isGridMode = !isGridMode
            viewModel.saveLayoutPreference(isGridMode)
            viewModel.getLayoutPreference()
        }
        binding.searchBarRecyclerView.adapter = searchAdapter
        binding.searchView.setupWithSearchBar(binding.searchBar)
        binding.searchView.editText.observeTextChanges().filter { it okWith MINIMUM_SEARCH_LENGTH }
            .debounce(MILLISECONDS)
            .distinctUntilChanged()
            .onEach {
                viewModel.getMovieByName(it)
            }
            .launchIn(lifecycleScope)
        showBottomNavigationBar()
    }


    private fun getUiState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeUiState.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchUiState.collectLatest {
                    searchAdapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.layoutPreference.collect { isGridManager ->
                    isGridMode = isGridManager
                    layoutManagerState = binding.recyclerView.layoutManager?.onSaveInstanceState()
                    if (isGridManager) {
                        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
                        adapter.setGridMode(true)
                        binding.layoutManagerButton.setImageResource(R.drawable.icon_grid_layout)
                    } else {
                        binding.recyclerView.layoutManager = LinearLayoutManager(context)
                        adapter.setGridMode(false)
                        binding.layoutManagerButton.setImageResource(R.drawable.icon_linear_layout)
                    }
                    binding.recyclerView.layoutManager?.onRestoreInstanceState(layoutManagerState)
                }
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.Loading || loadStates.append is LoadState.Loading) {
                    showProgressBar()
                } else {
                    hideProgressBar()
                }
                if (loadStates.refresh is LoadState.Error || loadStates.append is LoadState.Error) {
                    hideProgressBar()
                }
            }
        }

        lifecycleScope.launch {
            searchAdapter.loadStateFlow.collectLatest { loadStates->
                if (loadStates.refresh is LoadState.Error && binding.searchView.editText.isShown) {
                    showSnackbar(getString(R.string.something_went_wrong),null,null)
                }
                val isListEmpty = searchAdapter.itemCount == 0 &&
                        loadStates.refresh is LoadState.NotLoading &&
                        loadStates.append.endOfPaginationReached
                if (isListEmpty && binding.searchView.editText.isShown) {
                    showSnackbar(getString(R.string.not_found),null,null)
                }
            }
        }

        setOnNetworkAvailableCall {
            viewModel.getPopularMovie()
        }
    }

    private fun setUpAdapter() {
        binding.recyclerView.adapter = adapter
    }

    companion object {
        private const val MILLISECONDS = 200L
        private const val MINIMUM_SEARCH_LENGTH = 3
    }
}