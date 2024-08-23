package com.emrekizil.feature_search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.emrekizil.core_ui.component.BaseMovieUiState
import com.emrekizil.core_ui.databinding.ItemMovieGridBinding

class SearchPagingAdapter :
    PagingDataAdapter<BaseMovieUiState, SearchPagingAdapter.SearchViewHolder>(
        MovieDiffCallback()
    ) {

    private var onMovieItemClickListener: ((Int) -> Unit)? = null

    fun setOnMovieItemClickListener(onMovieItemClickListener: ((Int) -> Unit)?) {
        this.onMovieItemClickListener = onMovieItemClickListener
    }

    class SearchViewHolder(
        private val binding: ItemMovieGridBinding,
        private val onMovieItemClickListener: ((Int) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BaseMovieUiState?) {
            binding.root.setOnClickListener {
                onMovieItemClickListener?.invoke(item!!.id)
            }
            if (item != null) {
                binding.gridComponent.setMovieData(item)
            }
        }
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder {
        val binding =
            ItemMovieGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding, onMovieItemClickListener)
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<BaseMovieUiState>() {
        override fun areItemsTheSame(
            oldItem: BaseMovieUiState,
            newItem: BaseMovieUiState
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BaseMovieUiState,
            newItem: BaseMovieUiState
        ): Boolean {
            return oldItem == newItem
        }
    }
}