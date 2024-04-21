package com.example.alpha.ui.permissions.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import com.example.alpha.BuildConfig
import com.example.alpha.R

interface PermissionManager {
    companion object {
        private val PREFS_NAME = "MyPrefsFile"
        private val PREF_IS_AUTH_KEY_SAVED = "isAuthKeySaved"

        fun checkIfCameraPermissionIsGranted(context: Context): Boolean {
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED)
        }

        fun checkIfLocationPermissionIsGranted(context: Context): Boolean {
            return (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
                    == PackageManager.PERMISSION_GRANTED
                    &&
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    == PackageManager.PERMISSION_GRANTED)
        }

        fun checkIfAuthIsGranted(context: Context): Boolean {
            val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            val isAuthKeySaved = prefs.getBoolean(PREF_IS_AUTH_KEY_SAVED, false)
            return isAuthKeySaved
        }

        fun setAuthIsGranted(context: Context, isAuthKeySaved: Boolean) {
            val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            prefs.edit().putBoolean(PREF_IS_AUTH_KEY_SAVED, isAuthKeySaved).apply()
        }

        fun checkIsFirstRun(context: Context): Boolean {
            val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            val isFirstRun = prefs.getBoolean("isFirstRun", true)
            if (isFirstRun) {
                prefs.edit().putBoolean("isFirstRun", false).apply()
            }
            return isFirstRun
        }
    }
}
