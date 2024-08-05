package com.emrekizil.movieapp.ui.popular

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.emrekizil.movieapp.MainActivity
import com.emrekizil.movieapp.databinding.FragmentHomeBinding
import com.emrekizil.movieapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private val adapter = MoviePagingAdapter()

    private var layoutManagerState: Parcelable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        getData()
        binding.toggleButton.setOnCheckedChangeListener { _, isChecked ->
            layoutManagerState = binding.recyclerView.layoutManager?.onSaveInstanceState()
            if (isChecked) {
                binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
                adapter.setGridMode(true)
            } else {
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                adapter.setGridMode(false)
            }
            binding.recyclerView.layoutManager?.onRestoreInstanceState(layoutManagerState)
        }
    }

    private fun getData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeUiState.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun setUpAdapter() {
        binding.recyclerView.adapter = adapter
    }
}