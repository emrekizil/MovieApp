package com.emrekizil.movieapp.ui.detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.emrekizil.movieapp.R
import com.emrekizil.movieapp.databinding.FragmentDetailBinding
import com.emrekizil.movieapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel: DetailViewModel by viewModels()
    private val safeArgs:DetailFragmentArgs by navArgs()
    override fun observeUi() {
        super.observeUi()
        hideBottomNavigationBar()

        viewModel.getMovie(safeArgs.id)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.detailUiState.collectLatest {
                    when(it){
                        is MovieUiState.Error -> {
                            println(it.message)
                        }
                        MovieUiState.Loading -> {
                            println(it)
                        }
                        is MovieUiState.Success -> {
                            Log.d("mylovedata","$it.data")
                        }
                    }
                }
            }
        }
    }
}