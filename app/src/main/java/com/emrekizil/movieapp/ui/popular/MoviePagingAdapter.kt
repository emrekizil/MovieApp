package com.emrekizil.movieapp.ui.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.emrekizil.movieapp.data.dto.popular.Result
import com.emrekizil.movieapp.databinding.ItemMovieBinding

class MoviePagingAdapter : PagingDataAdapter<Result, MoviePagingAdapter.MovieViewHolder>(MovieDiffCallback()) {

    class MovieViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data:Result?){
            binding.movieText.text = data?.title
        }
    }

    override fun onBindViewHolder(holder: MoviePagingAdapter.MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviePagingAdapter.MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(binding)
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

}