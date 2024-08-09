package com.emrekizil.movieapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.emrekizil.movieapp.data.dto.popular.Result
import com.emrekizil.movieapp.data.repository.Movie
import com.emrekizil.movieapp.databinding.ItemMovieGridBinding

class SearchPagingAdapter : PagingDataAdapter<Movie, SearchPagingAdapter.SearchViewHolder>(MovieDiffCallback()) {

    private var onMovieItemClickListener:((Int)->Unit)? = null

    fun setOnMovieItemClickListener(onMovieItemClickListener:((Int)->Unit)?){
        this.onMovieItemClickListener = onMovieItemClickListener
    }

    class SearchViewHolder(private val binding: ItemMovieGridBinding, private val onMovieItemClickListener: ((Int) -> Unit)?) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie?) {
            binding.movieImageView.load(
                item?.posterPath
            )
            binding.movieScore.text = item?.voteAverage.toString()
            binding.root.setOnClickListener {
                onMovieItemClickListener?.invoke(item!!.id)
            }
        }
    }

    override fun onBindViewHolder(holder: SearchPagingAdapter.SearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchPagingAdapter.SearchViewHolder {
        val binding =
            ItemMovieGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding,onMovieItemClickListener)
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}