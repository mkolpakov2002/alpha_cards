package com.example.alpha.ui.api.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpha.data.Repository
import com.example.alpha.data.api.Hardware
import com.example.alpha.data.api.Item
import com.example.alpha.data.api.ItemCreateRequest
import kotlinx.coroutines.launch
import timber.log.Timber

class ItemViewModel : ViewModel() {

    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item> = _item

    private val _hardwareList = MutableLiveData<List<Hardware>>()
    val hardwareList: LiveData<List<Hardware>> = _hardwareList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private lateinit var repository: Repository


    fun getItem(token: String, itemId: Int) {
        Timber.plant(Timber.DebugTree())
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

    fun getHardwareList(token: String) {
        Timber.plant(Timber.DebugTree())
        if (!this::repository.isInitialized) {
            repository = Repository.getInstance(token)
        }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getHardwareItems()
                _hardwareList.value = result
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
        if (!this::repository.isInitialized) {
            repository = Repository.getInstance(token)
        }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.createItem(
                    ItemCreateRequest(
                        id = item.id,
                        name = item.name,
                        inv_key = item.inv_key,
                        hardware = item.hardware,
                        room = item.room?:0,
                        status = item.status,
                        owner = item.owner,
                        place = item.place,
                        available = item.available,
                        specifications = item.specifications,
                        created = System.currentTimeMillis().toString(),
                        updated = System.currentTimeMillis().toString()
                    )
                )
                Timber.tag("ItemViewModel").d(result.toString())
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