package com.emrekizil.movieapp.ui.popular

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.emrekizil.movieapp.MainActivity
import com.emrekizil.movieapp.R
import com.emrekizil.movieapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private val adapter = MoviePagingAdapter()

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        getData()
        binding.button.setOnClickListener {
            //(activity as MainActivity).hideBottomNavigation()
            //binding.recyclerView.layoutManager = GridLayoutManager(context,2)
        }
        binding.button2.setOnClickListener {
            //binding.recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun getData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
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