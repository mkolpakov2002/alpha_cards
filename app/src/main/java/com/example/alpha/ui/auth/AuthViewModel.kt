package com.example.alpha.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alpha.data.api.AuthResult
import com.example.alpha.ui.permissions.permission.PermissionManager

class AuthViewModel : ViewModel() {

    private val _authResult = MutableLiveData<AuthResult?>()
    val authResult: LiveData<AuthResult?> = _authResult

    private val _isAuthGranted = MutableLiveData<Boolean>()
    val isAuthGranted: LiveData<Boolean> = _isAuthGranted

    fun setAuthResult(result: AuthResult?) {
        _authResult.postValue(result)
        updateAuthState()
    }

    fun isAuthIsGranted(): Boolean {
        updateAuthState()
        return !authResult.value?.jwtToken.isNullOrEmpty()
    }

    fun updateAuthState() {
        _isAuthGranted.postValue(PermissionManager.checkIfAuthIsGranted())
    }
}