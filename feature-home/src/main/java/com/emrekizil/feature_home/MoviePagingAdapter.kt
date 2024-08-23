package com.emrekizil.feature_home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.emrekizil.core_ui.databinding.ItemMovieGridBinding
import com.emrekizil.core_ui.databinding.ItemMovieLinearBinding


class MoviePagingAdapter(
    private val onClick: (Int) -> Unit
) : PagingDataAdapter<com.emrekizil.core_ui.component.BaseMovieUiState, RecyclerView.ViewHolder>(
    MovieDiffCallback()
) {

    private var isGridMode = false

    fun setGridMode(isGrid: Boolean) {
        this.isGridMode = isGrid
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGridMode) GRID_VIEW_TYPE else LINEAR_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.setOnClickListener {
           onClick(movie!!.id!!)
        }
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
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == GRID_VIEW_TYPE) {
            val view = ItemMovieGridBinding.inflate(inflater, parent, false)
            GridViewHolder(view)
        } else {
            val view = ItemMovieLinearBinding.inflate(inflater, parent, false)
            LinearViewHolder(view)
        }
    }

    class LinearViewHolder(private val binding: ItemMovieLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: com.emrekizil.core_ui.component.BaseMovieUiState) {
            binding.linearComponent.setMovieData(movie)
        }
    }

    class GridViewHolder(private val binding: ItemMovieGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: com.emrekizil.core_ui.component.BaseMovieUiState) {
            binding.gridComponent.setMovieData(movie)
        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<com.emrekizil.core_ui.component.BaseMovieUiState>() {
        override fun areItemsTheSame(oldItem: com.emrekizil.core_ui.component.BaseMovieUiState, newItem: com.emrekizil.core_ui.component.BaseMovieUiState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: com.emrekizil.core_ui.component.BaseMovieUiState, newItem: com.emrekizil.core_ui.component.BaseMovieUiState): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val GRID_VIEW_TYPE = 1
        private const val LINEAR_VIEW_TYPE = 2
    }

}