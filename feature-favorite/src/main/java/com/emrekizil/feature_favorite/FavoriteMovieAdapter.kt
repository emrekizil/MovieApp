package com.emrekizil.feature_favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emrekizil.core_model.MovieDetail
import com.emrekizil.core_ui.databinding.LayoutMovieLinearBinding
import com.emrekizil.core_ui.utils.loadImage

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
        private val binding: LayoutMovieLinearBinding,
        private val onMovieItemClickListener: ((Int) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieDetail) {
            with(binding){
                movieImageView.loadImage(item.backdropPath)
                movieScore.text = item.voteAverage.toString()
                movieTitle.text = item.title
                root.setOnClickListener {
                    onMovieItemClickListener?.invoke(item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val binding =
            LayoutMovieLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMovieViewHolder(binding, onMovieItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun getItem(position: Int) = items[position]
}