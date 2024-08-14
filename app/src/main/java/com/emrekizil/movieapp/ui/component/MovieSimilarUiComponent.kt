package com.emrekizil.movieapp.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.emrekizil.movieapp.data.repository.model.Movie
import com.emrekizil.movieapp.databinding.LayoutSimilarMovieBinding
import com.emrekizil.movieapp.utils.loadImage

class MovieSimilarUiComponent @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?=null,
    defStyleAttr:Int = 0
) : FrameLayout(context,attributeSet,defStyleAttr) {
    private val binding = LayoutSimilarMovieBinding.inflate(LayoutInflater.from(context),this,false)

    init {
        addView(binding.root)
    }

    fun setMovieData(movie: Movie) {
        binding.movieScore.text = movie.voteAverage.toString()
        binding.movieImageView.loadImage(movie.backdropPath)
        binding.movieSimilarTitle.text = movie.title
    }
}