package com.emrekizil.movieapp.ui.favorite

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.emrekizil.movieapp.R
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
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.favoriteUiState.collectLatest {
                    adapter.updateItems(it)
                }
            }
        }
        showBottomNavigationBar()
    }

}