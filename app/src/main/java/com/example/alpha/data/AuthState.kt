package com.example.alpha.data

sealed class AuthState {
    object Success : AuthState()
    data class Error(val message: String?) : AuthState()
}