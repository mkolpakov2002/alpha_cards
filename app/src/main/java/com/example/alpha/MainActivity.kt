package com.example.alpha

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration.Builder
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.alpha.databinding.ActivityMainBinding
import com.example.alpha.ui.auth.AuthViewModel
import com.example.alpha.ui.permissions.permission.PermissionManager.Companion.checkIsFirstRun
import android.Manifest
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.alpha.ui.permissions.permission.PermissionManager


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var authViewModel: AuthViewModel
    private var isAuthGranted: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = Builder(
            R.id.navigation_home, R.id.userProfileFragment
        ).build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        authViewModel.isAuthGranted.observe(this, Observer { isGranted ->
            if(isAuthGranted != isGranted) {
                onAuthStateChanged(isGranted)
                isAuthGranted = isGranted
            }
        })

        if (savedInstanceState == null) {
            if (checkIsFirstRun()) {
                navigateToGreetingScreen()
            } else {
                checkPermissionsAndNavigate()
            }
        }
    }

    private fun onAuthStateChanged(isAuthGranted: Boolean) {
        if (isAuthGranted) {
            checkPermissionsAndNavigate()
        } else {
            navigateToAuthScreen()
        }
    }

    private fun checkPermissionsAndNavigate() {
        when {
            !PermissionManager.checkIfCameraPermissionIsGranted() -> {
                navigateToPermissionScreen(Manifest.permission.CAMERA)
            }
            !PermissionManager.checkIfLocationPermissionIsGranted() -> {
                navigateToPermissionScreen(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            else -> {
                navigateToHomeScreen()
            }
        }
    }

    private fun navigateToGreetingScreen() {
        navController.navigate(R.id.greetingFragment)
    }

    private fun navigateToPermissionScreen(permission: String) {
        when (permission) {
            Manifest.permission.CAMERA -> {
                navController.navigate(R.id.permissionCameraFragment)
            }
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION -> {
                navController.navigate(R.id.permissionLocationFragment)
            }
        }
    }

    private fun navigateToAuthScreen() {
        navController.navigate(R.id.authFragment)
    }

    private fun navigateToHomeScreen() {
        navController.navigate(R.id.navigation_home)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private val destinationListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        val isSpecialDestination = listOf(
            R.id.greetingFragment,
            R.id.permissionCameraFragment,
            R.id.permissionLocationFragment,
            R.id.permissionGrantedDialog,
            R.id.authFragment,
            R.id.liveBarcodeScanningFragment
        ).contains(destination.id)

        binding.materialToolbar.isVisible = !isSpecialDestination
        binding.navView.isVisible = !isSpecialDestination
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(destinationListener)
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(destinationListener)
        super.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        checkPermissionsAndNavigate()
    }
}