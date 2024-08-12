package com.emrekizil.movieapp.ui.detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.emrekizil.movieapp.R
import com.emrekizil.movieapp.databinding.FragmentDetailBinding
import com.emrekizil.movieapp.ui.base.BaseFragment
import com.emrekizil.movieapp.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel: DetailViewModel by viewModels()
    private val safeArgs: DetailFragmentArgs by navArgs()
    override fun observeUi() {
        super.observeUi()
        hideBottomNavigationBar()
        viewModel.getMovie(safeArgs.id)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailUiState.collectLatest {
                    when (it) {
                        is MovieDetailScreenUiState.Error -> {
                            hideProgressBar()
                        }

                        MovieDetailScreenUiState.Loading -> {
                            showProgressBar()
                        }

                        is MovieDetailScreenUiState.Success -> {
                            hideProgressBar()
                            setUi(it.data)
                        }
                    }
                }
            }
        }
    }

    private fun setUi(data: MovieUiState) {
        with(binding){
            movieDetailImageView.loadImage(data.backdropPath)
            voteTextView.text = data.voteAverage.toString()
            movieTitle.text = data.title
            releaseDateTextView.text = data.releaseDate
            movieOverview.text = data.overview
            favoriteButton.setOnClickListener {
                data.onFavorite.invoke()
            }
            favoriteButton.setImageResource(if (data.isFavorite) R.drawable.icon_delete_favorite else R.drawable.icon_add_favorite)
        }
    }
}