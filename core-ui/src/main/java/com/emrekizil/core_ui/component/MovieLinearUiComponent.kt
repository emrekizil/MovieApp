package com.emrekizil.core_ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.emrekizil.core_ui.R
import com.emrekizil.core_ui.databinding.LayoutMovieLinearBinding
import com.emrekizil.core_ui.utils.loadImage


class MovieLinearUiComponent @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?=null,
    defStyleAttr:Int = 0
) : FrameLayout(context,attributeSet,defStyleAttr) {
    private val binding = LayoutMovieLinearBinding.inflate(LayoutInflater.from(context),this,false)

    init {
        addView(binding.root)
    }

    fun setMovieData(movieUiState: BaseMovieUiState) {
        binding.movieScore.text = movieUiState.voteAverage.toString()
        binding.movieImageView.loadImage(movieUiState.backdropPath)
        binding.iconIsFavorite.setImageResource(if (movieUiState.isFavorite) R.drawable.icon_delete_favorite else R.drawable.icon_add_favorite)
        binding.movieTitle.text = movieUiState.title
    }
}