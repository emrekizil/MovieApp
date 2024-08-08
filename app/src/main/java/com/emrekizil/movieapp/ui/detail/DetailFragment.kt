package com.emrekizil.movieapp.ui.detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emrekizil.movieapp.R
import com.emrekizil.movieapp.databinding.FragmentDetailBinding
import com.emrekizil.movieapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel: DetailViewModel by viewModels()
    override fun observeUi() {
        super.observeUi()
        hideBottomNavigationBar()
    }
}