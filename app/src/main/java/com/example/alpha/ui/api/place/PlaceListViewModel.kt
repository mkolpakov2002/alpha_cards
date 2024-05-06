package com.example.alpha.ui.api.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpha.data.Repository
import com.example.alpha.data.api.PlaceItem
import com.example.alpha.data.api.Section
import kotlinx.coroutines.launch
import timber.log.Timber

class PlaceListViewModel : ViewModel() {

    private val _items = MutableLiveData<List<PlaceItem>>()
    val items: LiveData<List<PlaceItem>> = _items

    private val _sections = MutableLiveData<List<Section>>()
    val sections: LiveData<List<Section>> = _sections

    private val _selectedItems = mutableSetOf<PlaceItem>()
    private val _selectedItemCount = MutableLiveData<Int>()
    val selectedItemCount: LiveData<Int> = _selectedItemCount

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private lateinit var repository: Repository

    fun refreshItems(token: String, sectionId: Int? = null) {
        if (!this::repository.isInitialized) {
            repository = Repository.getInstance(token)
        }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getPlaceItems()
                if(sectionId != null && sectionId != -1000) {
                    result.filter { it.section == sectionId }
                }
                _items.value = result

                val sectionsResult = repository.getSectionItems()
                _sections.value = sectionsResult

                _isLoading.value = false
            } catch (e: Exception) {
                Timber.tag("ItemListViewModel").e(e.toString())
                _errorMessage.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onItemClick(item: PlaceItem) {
        // Handle item click event
        // Navigate to the item details fragment
    }

    fun onItemLongClick(item: PlaceItem) {
        if (_selectedItems.contains(item)) {
            _selectedItems.remove(item)
        } else {
            _selectedItems.add(item)
        }
        _selectedItemCount.value = _selectedItems.size
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