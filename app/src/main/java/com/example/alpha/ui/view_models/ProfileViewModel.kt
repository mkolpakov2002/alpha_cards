package com.example.alpha.ui.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.alpha.data.api.User
import com.example.alpha.domain.Repository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository, private val userId: Int) : ViewModel() {
    val user: LiveData<com.example.alpha.data.api.User> = liveData {
        repository.getUserById(userId)?.let { emit(it) }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            // Navigate to AuthFragment
        }
    }
}