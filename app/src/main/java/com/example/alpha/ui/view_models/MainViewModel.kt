package com.example.alpha.ui.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpha.data.api.Laboratory
import com.example.alpha.domain.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val laboratoryRepository: Repository) : ViewModel() {
    private val _laboratories = MutableLiveData<List<com.example.alpha.data.api.Laboratory>>()
    val laboratories: LiveData<List<com.example.alpha.data.api.Laboratory>> = _laboratories

    private val _navigateToElementsList = MutableLiveData<com.example.alpha.data.api.Laboratory>()
    val navigateToElementsList: LiveData<com.example.alpha.data.api.Laboratory> = _navigateToElementsList

    init {
        loadLaboratories()
    }

    private fun loadLaboratories() {
        viewModelScope.launch {
            try {
                val result = laboratoryRepository.getAllLaboratories()
                _laboratories.value = result
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun onLaboratorySelected(laboratory: com.example.alpha.data.api.Laboratory) {
        _navigateToElementsList.value = laboratory
    }
}