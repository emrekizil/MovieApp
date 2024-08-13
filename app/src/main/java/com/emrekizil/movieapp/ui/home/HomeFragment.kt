package com.emrekizil.movieapp.ui.home

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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

    private val adapter = MoviePagingAdapter { result ->
        navigateToDetailFragment(result)
    }

    private val searchAdapter = SearchPagingAdapter().apply {
        setOnMovieItemClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            navigate(action)
        }
    }

    private var layoutManagerState: Parcelable? = null

    private var isGridMode = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        getData()
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

    private fun getData() {
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
                    if (isGridManager){
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
    }

    private fun setUpAdapter() {
        binding.recyclerView.adapter = adapter
    }

    private fun navigateToDetailFragment(movieId: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId)
        navigate(action)
    }

    companion object {
        private const val MILLISECONDS = 200L
        private const val MINIMUM_SEARCH_LENGTH = 3
    }
}