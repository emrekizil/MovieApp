package com.emrekizil.core_ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkObserver @Inject constructor(@ApplicationContext val context:Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var isNetworkAvailable = false
    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    private var isCallbackRegistered = false
    fun startNetworkCallback(
        onNetworkLost: () -> Unit,
        onNetworkAvailable: () -> Unit
    ) {
        if (isCallbackRegistered) return
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        networkCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                if (!isNetworkAvailable) {
                    isNetworkAvailable = true
                    onNetworkAvailable()
                }
            }

            override fun onLost(network: Network) {
                isNetworkAvailable = false
                onNetworkLost()
            }
        }
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback!!)
        isCallbackRegistered = true
        isNetworkAvailable = connectivityManager.activeNetwork != null
    }

    fun stopNetworkCallback() {
        if (isCallbackRegistered && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback!!)
            isCallbackRegistered = false
        }
    }

}