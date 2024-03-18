package com.example.alpha.ui.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpha.data.AuthState
import com.example.alpha.domain.Repository
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: Repository) : ViewModel() {
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val authState = MutableLiveData<AuthState>()

    fun login() {
        viewModelScope.launch {
            try {
                val result = authRepository.login(username.value!!, password.value!!)
                authState.value = if (result.isSuccess) {
                    AuthState.Success
                } else {
                    val exception = result.exceptionOrNull()
                    AuthState.Error(exception?.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                authState.value = AuthState.Error(e.message ?: "Exception occurred")
            }
        }
    }
}