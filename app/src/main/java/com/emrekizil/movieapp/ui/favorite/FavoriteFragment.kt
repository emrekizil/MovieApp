package com.emrekizil.movieapp.ui.favorite

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.emrekizil.movieapp.R
import com.emrekizil.movieapp.data.repository.model.MovieDetail
import com.emrekizil.movieapp.databinding.FragmentFavoriteBinding
import com.emrekizil.movieapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private val viewModel: FavoriteViewModel by viewModels()
    private val adapter by lazy {
        FavoriteMovieAdapter().apply {
            setOnMovieItemClickListener {
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it)
                navigate(action)
            }
        }
    }

    override fun observeUi() {
        super.observeUi()
        viewModel.getFavoriteMovies()
        binding.favoriteRecyclerView.adapter = this.adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteUiState.collectLatest { data ->
                    adapter.updateItems(data)
                    showFavoriteUiState(data)
                }
            }
        }
        showBottomNavigationBar()
        initView()
    }

    private fun showFavoriteUiState(data: List<MovieDetail>) {
        if (data.isEmpty()) {
            with(binding) {
                emptyListImageView.visibility = View.VISIBLE
                emptyListHeaderTextView.visibility = View.VISIBLE
                emptyListOverviewTextView.visibility = View.VISIBLE
            }
        } else {
            with(binding) {
                emptyListImageView.visibility = View.GONE
                emptyListHeaderTextView.visibility = View.GONE
                emptyListOverviewTextView.visibility = View.GONE
            }
        }
    }

    private fun initView() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val movie = adapter.getItem(position)
                viewModel.deleteMovie(movie)
                showSnackbar(
                    getString(R.string.movie_successfully_deleted),
                    getString(R.string.undo)
                ) {
                    viewModel.insertMovie(movie)
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.favoriteRecyclerView)
    }
}