package com.emrekizil.movieapp.ui.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.emrekizil.movieapp.data.dto.popular.Result
import com.emrekizil.movieapp.databinding.ItemMovieGridBinding
import com.emrekizil.movieapp.databinding.ItemMovieLinearBinding

class MoviePagingAdapter : PagingDataAdapter<Result, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    private var isGridMode = false

    fun setGridMode(isGrid: Boolean) {
        this.isGridMode = isGrid
    }


    /*class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Result?) {
            binding.movieText.text = data?.title
        }
    }*/

    /*override fun onBindViewHolder(holder: MoviePagingAdapter.MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }*/

    override fun getItemViewType(position: Int): Int {
        return if (isGridMode) GRID_VIEW_TYPE else LINEAR_VIEW_TYPE
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            if (getItemViewType(position) == GRID_VIEW_TYPE) {
                (holder as GridViewHolder).bind(movie)
            } else {
                (holder as LinearViewHolder).bind(movie)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        /*val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)*/
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == GRID_VIEW_TYPE) {
            val view = ItemMovieGridBinding.inflate(inflater,parent, false)
            GridViewHolder(view)
        } else {
            val view = ItemMovieLinearBinding.inflate(inflater,parent,false)
            LinearViewHolder(view)
        }
    }

    class LinearViewHolder(private val binding: ItemMovieLinearBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Result) {
            binding.movieTitle.text = movie.title
            binding.movieImageView.load(
               movie.getPosterImageUrl()
            ){
                scale(Scale.FILL)
            }
            binding.movieScore.text = movie.voteAverage.toString()
        }
    }

    class GridViewHolder(private val binding: ItemMovieGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Result) {
            binding.movieImageView.load(
                movie.getPosterImageUrl()
            ){
                scale(Scale.FILL)
            }
            binding.movieScore.text = movie.voteAverage.toString()
        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val GRID_VIEW_TYPE = 1
        private const val LINEAR_VIEW_TYPE = 2
    }

}