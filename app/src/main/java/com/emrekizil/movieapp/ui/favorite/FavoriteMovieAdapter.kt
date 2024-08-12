package com.emrekizil.movieapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emrekizil.movieapp.data.repository.MovieDetail
import com.emrekizil.movieapp.databinding.ItemMovieLinearBinding
import com.emrekizil.movieapp.utils.loadImage

class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>() {
    private var onMovieItemClickListener: ((Int) -> Unit)? = null

    fun setOnMovieItemClickListener(onMovieItemClickListener: ((Int) -> Unit)?) {
        this.onMovieItemClickListener = onMovieItemClickListener
    }

    private val items = mutableListOf<MovieDetail>()

    fun updateItems(newItems: List<MovieDetail>) {
        items.apply {
            clear()
            addAll(newItems)
            notifyDataSetChanged()
        }
    }

    class FavoriteMovieViewHolder(
        private val binding: ItemMovieLinearBinding,
        private val onMovieItemClickListener: ((Int) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieDetail) {
            binding.movieImageView.loadImage("https://image.tmdb.org/t/p/w400/${item.backdropPath}" )
            binding.movieScore.text = item.voteAverage.toString()
            binding.movieTitle.text = item.title
            binding.root.setOnClickListener {
                onMovieItemClickListener?.invoke(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val binding =
            ItemMovieLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMovieViewHolder(binding, onMovieItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
}