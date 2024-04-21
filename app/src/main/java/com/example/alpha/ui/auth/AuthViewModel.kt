package com.example.alpha.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alpha.data.api.AuthResult

class AuthViewModel : ViewModel() {
    private val _authResult = MutableLiveData<AuthResult?>()
    val authResult: LiveData<AuthResult?> = _authResult

    fun setAuthResult(result: AuthResult?) {
        _authResult.postValue(result)
    }

    fun isAuthIsGranted(): Boolean {
        return !authResult.value?.jwtToken.isNullOrEmpty()
    }
}