package com.example.alpha.ui.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _navigateToAuth = MutableLiveData<Boolean>()
    val navigateToAuth: LiveData<Boolean> = _navigateToAuth

    init {
        viewModelScope.launch {
            delay(SPLASH_DELAY)
            _navigateToAuth.value = true
        }
    }

    companion object {
        private const val SPLASH_DELAY = 2000L // 2 seconds
    }
}