package com.example.alpha.ui.api.building

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpha.data.Repository
import com.example.alpha.data.api.BuildingItem

import kotlinx.coroutines.launch
import timber.log.Timber

class BuildingListViewModel : ViewModel() {

    private val _items = MutableLiveData<List<BuildingItem>>()
    val items: LiveData<List<BuildingItem>> = _items

    private val _selectedItems = mutableSetOf<BuildingItem>()
    private val _selectedItemCount = MutableLiveData<Int>()
    val selectedItemCount: LiveData<Int> = _selectedItemCount

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private lateinit var repository: Repository

    fun refreshItems(token: String) {
        if (!this::repository.isInitialized) {
            repository = Repository.getInstance(token)
        }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getBuildingItems()
                _items.value = result
            } catch (e: Exception) {
                Timber.tag("ItemListViewModel").e(e.toString())
                _errorMessage.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onAddItemClick() {
        // Handle add item click event
        // Navigate to the add item fragment
    }

    fun onEditItemClick() {
        if (_selectedItems.size == 1) {
            val item = _selectedItems.first()
            // Navigate to the edit item fragment with the selected item
        }
    }

    fun onDeleteItemClick(token: String) {
        // Delete the selected items from the database or API
        _selectedItems.clear()
        _selectedItemCount.value = 0
        refreshItems(token)
    }
}