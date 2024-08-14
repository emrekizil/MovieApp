package com.emrekizil.movieapp.ui.detail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.emrekizil.movieapp.R
import com.emrekizil.movieapp.data.repository.model.Movie
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
        viewModel.getSimilarMovie(safeArgs.id)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailUiState.collectLatest {
                    when (it) {
                        is MovieDetailScreenUiState.Error -> {
                            hideProgressBar()
                            viewGone()
                        }

                        MovieDetailScreenUiState.Loading -> {
                            showProgressBar()
                            viewVisible()
                        }

                        is MovieDetailScreenUiState.Success -> {
                            hideProgressBar()
                            setUi(it.data)
                            viewVisible()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.similarMovieUiState.collect {
                    if (it.isNotEmpty()) {
                        setSimilarUi(it)
                    }
                }
            }
        }
        setOnNetworkAvailableCall {
            viewModel.getMovie(safeArgs.id)
            viewModel.getSimilarMovie(safeArgs.id)
        }

    }

    private fun viewVisible() {
        with(binding) {
            somethingWrongTextView.visibility = View.GONE
            somethingWrongImageView.visibility = View.GONE
            scrollView.visibility = View.VISIBLE
        }
    }

    private fun viewGone() {
        with(binding) {
            somethingWrongTextView.visibility = View.VISIBLE
            somethingWrongImageView.visibility = View.VISIBLE
            scrollView.visibility = View.GONE
        }
    }

    private fun setSimilarUi(movies: List<Movie>) {
        with(binding) {
            similarMovieOne.setMovieData(movies[0])
            similarMovieOne.setOnClickListener {
                val action = DetailFragmentDirections.actionDetailFragmentSelf(movies[0].id)
                navigate(action)
            }
            similarMovieSecond.setMovieData(movies[1])
            similarMovieSecond.setOnClickListener {
                val action = DetailFragmentDirections.actionDetailFragmentSelf(movies[1].id)
                navigate(action)
            }
            similarMovieThird.setMovieData(movies[2])
            similarMovieThird.setOnClickListener {
                val action = DetailFragmentDirections.actionDetailFragmentSelf(movies[2].id)
                navigate(action)
            }
        }
    }

    private fun setUi(data: MovieUiState) {
        with(binding) {
            movieDetailImageView.loadImage(data.backdropPath)
            voteTextView.text = data.voteAverage.toString()
            movieTitle.text = data.title
            releaseDateTextView.text = data.releaseDate.subSequence(0, 4)
            movieOverview.text = data.overview
            if (data.genres.isNotEmpty()) {
                binding.movieGenreFirst.text = data.genres[0]
                if (data.genres.size > 1) {
                    binding.movieGenreSecond.text = data.genres[1]
                }
            }
            favoriteButton.setOnClickListener {
                data.onFavorite.invoke()
            }
            favoriteButton.setImageResource(if (data.isFavorite) R.drawable.icon_delete_favorite else R.drawable.icon_add_favorite)
        }
    }

}