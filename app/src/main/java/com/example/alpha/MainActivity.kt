package com.example.alpha

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.AppBarConfiguration.Builder
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.alpha.databinding.ActivityMainBinding
import com.example.alpha.ui.auth.AuthViewModel
import com.example.alpha.ui.permissions.permission.PermissionManager.Companion.checkIsFirstRun
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var navView: BottomNavigationView? = null
    private var materialToolbar: MaterialToolbar? = null
    private lateinit var navController: NavController

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        materialToolbar = binding!!.materialToolbar
        setSupportActionBar(materialToolbar)
        navView = binding!!.navView
        authViewModel.isAuthGranted.observe(this) {
            if (!it) {
                navController.navigate(R.id.authFragment)
            }
        }
    }


    override fun onStart() {
        super.onStart()
        val appBarConfiguration: AppBarConfiguration = Builder(
            R.id.navigation_home, R.id.userProfileFragment
        )
            .build()
        navController = findNavController(this, R.id.nav_host_fragment_activity_main)
        navController.setGraph(R.navigation.mobile_navigation)
        setupActionBarWithNavController(this, navController, appBarConfiguration)
        setupWithNavController(binding!!.navView, navController)
        checkForGreeting()
    }

    private fun checkForGreeting() {
        if (checkIsFirstRun()) {
            navController.navigate(R.id.greetingFragment)
        } else {
            navController.navigate(R.id.navigation_home)
        }
    }

    private val listener = NavController.OnDestinationChangedListener { _, nd: NavDestination, _->
        if(nd.id==R.id.greetingFragment||
            nd.id==R.id.permissionCameraFragment||
            nd.id==R.id.permissionLocationFragment||
            nd.id==R.id.permissionGrantedDialog ||
            nd.id==R.id.authFragment ||
            nd.id==R.id.liveBarcodeScanningFragment) {
            materialToolbar?.visibility = View.GONE
            navView?.visibility = View.GONE
        } else {
            materialToolbar?.visibility = View.VISIBLE
            navView?.visibility = View.VISIBLE
        }
    }
    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(listener)
        super.onPause()
    }

}