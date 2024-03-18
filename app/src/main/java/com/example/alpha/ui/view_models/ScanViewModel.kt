package com.example.alpha.ui.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpha.data.api.Equipment
import com.example.alpha.domain.Repository
import kotlinx.coroutines.launch

class ScanViewModel(private val repository: Repository) : ViewModel() {
    private val _scannedEquipment = MutableLiveData<List<com.example.alpha.data.api.Equipment>>()
    val scannedEquipment: LiveData<List<com.example.alpha.data.api.Equipment>> = _scannedEquipment

}