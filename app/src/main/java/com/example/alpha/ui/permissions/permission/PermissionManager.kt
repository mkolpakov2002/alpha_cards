package com.example.alpha.ui.permissions.permission

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.ContextCompat
import com.example.alpha.App

interface PermissionManager {
    companion object {
        private val PREFS_NAME = "MyPrefsFile"
        private val PREF_IS_AUTH_KEY_SAVED = "isAuthKeySaved"

        fun checkIfCameraPermissionIsGranted(): Boolean {
            return (ContextCompat.checkSelfPermission(App.applicationContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED)
        }

        fun checkIfLocationPermissionIsGranted(): Boolean {
            return (ContextCompat.checkSelfPermission(
                App.applicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
                    == PackageManager.PERMISSION_GRANTED
                    &&
                    ContextCompat.checkSelfPermission(
                        App.applicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    == PackageManager.PERMISSION_GRANTED)
        }

        fun checkIfAuthIsGranted(): Boolean {
            val prefs = App.applicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            val isAuthKeySaved = prefs.getBoolean(PREF_IS_AUTH_KEY_SAVED, false)
            return isAuthKeySaved
        }

        fun getToken(): String? {
            val prefs = App.applicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            return prefs.getString("token", null)
        }

        fun setAuthIsGranted( isAuthKeySaved: Boolean, token: String? = null) {
            val prefs = App.applicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            prefs.edit().putBoolean(PREF_IS_AUTH_KEY_SAVED, isAuthKeySaved).apply()
            token?.let { prefs.edit().putString("token", it).apply() }
        }

        fun checkIsFirstRun(): Boolean {
            val prefs = App.applicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            val isFirstRun = prefs.getBoolean("isFirstRun", true)
            if (isFirstRun) {
                prefs.edit().putBoolean("isFirstRun", false).apply()
            }
            return isFirstRun
        }
    }
}
