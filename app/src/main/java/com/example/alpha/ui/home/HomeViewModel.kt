package com.example.alpha.ui.home

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpha.App
import com.example.alpha.data.Repository
import com.example.alpha.data.api.Room
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> = _rooms
    private lateinit var repository: Repository

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun refreshRooms(token: String) {
        if (!this::repository.isInitialized) {
            repository = Repository.getInstance(token)
        }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getRooms()
                _rooms.value = result
            } catch (e: Exception) {
                Log.e("HomeViewModel", e.toString())
                _errorMessage.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
}