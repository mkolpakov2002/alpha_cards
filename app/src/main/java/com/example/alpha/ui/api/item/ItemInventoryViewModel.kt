package com.example.alpha.ui.api.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpha.data.Repository
import com.example.alpha.data.api.Item
import kotlinx.coroutines.launch
import timber.log.Timber

class ItemInventoryViewModel : ViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    private val _scannedItems = mutableSetOf<String>()

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
                val result = repository.getItemItems()
                _items.value = result
                _isLoading.value = false
            } catch (e: Exception) {
                Timber.tag("InventoryViewModel").e(e.toString())
                _errorMessage.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun markItemAsScanned(invKey: String) {
        _scannedItems.add(invKey)
    }

    fun isItemScanned(item: Item): Boolean {
        return _scannedItems.contains(item.inv_key)
    }
}