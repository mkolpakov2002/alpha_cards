package com.example.alpha.ui.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.alpha.data.api.Element
import com.example.alpha.data.api.Equipment
import com.example.alpha.data.api.Laboratory
import com.example.alpha.domain.Repository

class LaboratoryDetailsViewModel(private val repository: Repository, private val labId: Int) : ViewModel() {
    val laboratory: LiveData<com.example.alpha.data.api.Laboratory> = liveData {
        repository.getLaboratoryById(labId)?.let { emit(it) }
    }

    val element: LiveData<List<com.example.alpha.data.api.Element>> = liveData {
        emit(repository.getElementsByLabId(labId))
    }

    fun startInventory() {
        // Navigate to ScanningFragment and pass labId
    }
}