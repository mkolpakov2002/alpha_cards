package com.example.alpha.ui.api.terminal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpha.data.Repository
import com.example.alpha.data.api.Terminal
import kotlinx.coroutines.launch
import timber.log.Timber

class TerminalListViewModel : ViewModel() {

    private val _items = MutableLiveData<List<Terminal>>()
    val items: LiveData<List<Terminal>> = _items

    private val _selectedItems = mutableSetOf<Terminal>()
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
                val result = repository.getTerminalItems()
                _items.value = result
            } catch (e: Exception) {
                Timber.tag("ItemListViewModel").e(e.toString())
                _errorMessage.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onItemClick(item: Terminal) {
        // Handle item click event
        // Navigate to the item details fragment
    }

    fun onItemLongClick(item: Terminal) {
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