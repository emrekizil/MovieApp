package com.emrekizil.movieapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.emrekizil.movieapp.MainActivity
import com.emrekizil.movieapp.utils.viewBinding

abstract class BaseFragment<T : ViewBinding>(factory: (LayoutInflater) -> T) : Fragment() {
    val binding: T by viewBinding(factory)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        observeUi()
    }

    open fun observeUi() = Unit

    open fun navigate(action: NavDirections) {
        findNavController().navigate(action)
    }

    fun hideBottomNavigationBar(){
        (activity as MainActivity).hideBottomNavigationBar()
    }

    fun showBottomNavigationBar(){
        (activity as MainActivity).showBottomNavigationBar()
    }
}