package com.emrekizil.movieapp.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.emrekizil.movieapp.R
import com.emrekizil.movieapp.databinding.LayoutMovieGridBinding
import com.emrekizil.movieapp.utils.loadImage

class MovieGridUiComponent @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?=null,
    defStyleAttr:Int = 0
) : FrameLayout(context,attributeSet,defStyleAttr){
    private val binding = LayoutMovieGridBinding.inflate(LayoutInflater.from(context),this,false)
    init {
        addView(binding.root)
    }

    fun setMovieData(movieUiState: BaseMovieUiState) {
        binding.movieScore.text = movieUiState.voteAverage.toString()
        binding.movieImageView.loadImage(movieUiState.posterPath)
        binding.iconIsFavorite.setImageResource(if (movieUiState.isFavorite) R.drawable.icon_delete_favorite else R.drawable.icon_add_favorite)
    }
}