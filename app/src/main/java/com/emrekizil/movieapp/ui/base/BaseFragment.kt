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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    fun hideProgressBar(){
        (activity as MainActivity).hideProgressBar()
    }

    fun showProgressBar(){
        (activity as MainActivity).showProgressBar()
    }

    fun showSnackbar(snackBarText: String, actionName: String?, action: (() -> Unit)?){
        (activity as MainActivity).showSnackBar(snackBarText, actionName, action)
    }

    fun setOnNetworkAvailableCall(networkCall: () -> Unit) {
        (activity as MainActivity).setOnNetworkAvailableCall(networkCall)
    }

}