package com.example.alpha.ui.api.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpha.data.Repository
import com.example.alpha.data.api.BuildingItem
import com.example.alpha.data.api.LabItem
import com.example.alpha.data.api.Room
import com.example.alpha.data.api.Section
import kotlinx.coroutines.launch
import timber.log.Timber

class RoomListViewModel : ViewModel() {

    private val _items = MutableLiveData<List<Room>>()
    val items: LiveData<List<Room>> = _items

    private val _labItems = MutableLiveData<List<LabItem>>()
    val labItems: LiveData<List<LabItem>> = _labItems
    private val _buildingItems = MutableLiveData<List<BuildingItem>>()
    val buildingItems: LiveData<List<BuildingItem>> = _buildingItems

    private val _selectedItems = mutableSetOf<Room>()
    private val _selectedItemCount = MutableLiveData<Int>()
    val selectedItemCount: LiveData<Int> = _selectedItemCount

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private lateinit var repository: Repository

    fun refreshItems(token: String, buildingId: Int? = null) {
        Timber.plant(Timber.DebugTree())
        if (!this::repository.isInitialized) {
            repository = Repository.getInstance(token)
        }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                var result = repository.getRoomItems()
                if(buildingId != null && buildingId != -1000) {
                    result = result.filter { it.building == buildingId }
                }
                _items.value = result

                val labResult = repository.getLabItems()
                _labItems.value = labResult

                val buildingResult = repository.getBuildingItems()
                _buildingItems.value = buildingResult

                _isLoading.value = false
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
        refreshItems(token, null)
    }
}