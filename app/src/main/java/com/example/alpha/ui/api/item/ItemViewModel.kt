package com.example.alpha.ui.api.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpha.data.Repository
import com.example.alpha.data.api.Item
import kotlinx.coroutines.launch
import timber.log.Timber

class ItemViewModel : ViewModel() {

    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item> = _item

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private lateinit var repository: Repository


    fun getItem(token: String, itemId: Int) {
        if (!this::repository.isInitialized) {
            repository = Repository.getInstance(token)
        }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getItemById(itemId)
                _item.value = result
                _isLoading.value = false
            } catch (e: Exception) {
                Timber.tag("ItemViewModel").e(e.toString())
                _errorMessage.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateItem(token: String, item: Item) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
//                repository.updateItem(item)
                _item.value = item
                _isLoading.value = false
            } catch (e: Exception) {
                Timber.tag("ItemViewModel").e(e.toString())
                _errorMessage.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateInvKey(invKey: String) {
        _item.value?.let { currentItem ->
            val updatedItem = currentItem.copy(inv_key = invKey)
            _item.value = updatedItem
        }
    }
}