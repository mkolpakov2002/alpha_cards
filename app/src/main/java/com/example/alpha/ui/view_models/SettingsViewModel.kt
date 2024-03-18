package com.example.alpha.ui.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpha.data.api.Settings
import com.example.alpha.domain.Repository
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsRepository: Repository) : ViewModel() {
    val language = MutableLiveData<String>()
    val theme = MutableLiveData<String>()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            try {
                val settings = settingsRepository.getSettings()
                language.value = settings.language
                theme.value = settings.theme
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun saveSettings() {
        viewModelScope.launch {
            try {
                val settings = com.example.alpha.data.api.Settings(language.value!!, theme.value!!)
                settingsRepository.saveSettings(settings)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                settingsRepository.logout()
                // Navigate to login screen
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}