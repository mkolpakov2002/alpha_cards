package com.example.alpha

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.AppBarConfiguration.Builder
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.alpha.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var navView: BottomNavigationView? = null
    private var navController: NavController? = null
    private var materialToolbar: MaterialToolbar? = null
    var prefs: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        materialToolbar = binding!!.materialToolbar
        setSupportActionBar(materialToolbar)
        navView = binding!!.navView
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration: AppBarConfiguration = Builder(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
        )
            .build()
        navController = findNavController(this, R.id.nav_host_fragment_activity_main)
        setupActionBarWithNavController(this, navController!!, appBarConfiguration)
        setupWithNavController(binding!!.navView, navController!!)
        checkFirstRun()
    }

    private fun checkFirstRun() {
        val PREFS_NAME = "MyPrefsFile"
        val PREF_VERSION_CODE_KEY = "version_code"
        val DOESNT_EXIST = -1

        // Get current version code
        val currentVersionCode = BuildConfig.VERSION_CODE

        // Get saved version code
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST)

        // Check for first run or upgrade
        if (savedVersionCode == DOESNT_EXIST) {

            // TODO This is a new install (or the user cleared the shared preferences)
            navController?.navigate(R.id.greetingFragment)
        } else if (currentVersionCode == savedVersionCode) {
            // This is just a normal run
            return
        } else if (currentVersionCode > savedVersionCode) {
            navController?.navigate(R.id.greetingFragment)
            // TODO This is an upgrade
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply()
    }

    private val listener = NavController.OnDestinationChangedListener { _, nd: NavDestination, _->
        // react on change
        // you can check destination.id or destination.label and act based on that
        if(nd.id==R.id.greetingFragment||
            nd.id==R.id.permissionCameraFragment||
            nd.id==R.id.permissionLocationFragment||
            nd.id==R.id.permissionGrantedDialog) {
            materialToolbar?.visibility = View.GONE
            navView?.visibility = View.GONE
        } else {
            materialToolbar?.visibility = View.VISIBLE
            navView?.visibility = View.VISIBLE
        }
    }
    override fun onResume() {
        super.onResume()
        navController?.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        navController?.removeOnDestinationChangedListener(listener)
        super.onPause()
    }

}