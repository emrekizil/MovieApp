package com.emrekizil.core_ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.emrekizil.core_ui.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var networkObserver: NetworkObserver
    private var onNetworkAvailableCall: (() -> Unit)? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        networkObserver = NetworkObserver(this)
        networkObserver.startNetworkCallback(
            onNetworkLost = {
                showSnackBar(getString(R.string.internet_connection_lost))
            },
            onNetworkAvailable = {
                this.onNetworkAvailableCall?.let { it() }
            }
        )

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setupWithNavController(navController)
    }

    fun hideBottomNavigationBar() {
        binding.bottomNavigation.visibility = View.GONE
    }

    fun showBottomNavigationBar() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    fun showSnackBar(snackBarText: String, actionName: String? = null, action: (() -> Unit)? = null) {
        Snackbar.make(
            binding.root, snackBarText, Snackbar.LENGTH_SHORT
        ).setAction(actionName) {
            if (action != null) {
                action()
            }
        }.setAnchorView(binding.bottomNavigation).show()
    }

    fun setOnNetworkAvailableCall(networkCall: () -> Unit) {
        onNetworkAvailableCall = networkCall
    }

    override fun onDestroy() {
        super.onDestroy()
        networkObserver.stopNetworkCallback()
    }
}