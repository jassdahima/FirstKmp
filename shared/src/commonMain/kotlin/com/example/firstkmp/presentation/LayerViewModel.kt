package com.example.firstkmp.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstkmp.data.KtorClient
import com.example.firstkmp.data.LayerItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LayerViewModel(val client : KtorClient) : ViewModel(){

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()



    fun getAllLayers() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null)}

            try {
                val response = client.getLayer()
                _state.update { it.copy(isLoading = false, countryLayer = response)}
                _state.value = _state.value.copy(isLoading = false, countryLayer = response)
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message)}
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun onSearchTextChange(text : String){
        _state.update { it.copy(searchText = text) }
    }

    fun getLayerBySearch(countryName : String) {
        if (countryName.isBlank()) return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true)}
            try {
                val response = client.getLayerBySearch(countryName)
                _state.update { it.copy(isLoading = false, countryLayer = response)}
            } catch (e: Exception) {
               _state.update { it.copy(isLoading = false, error = e.message) }
            }
    }
        }

    fun refreshBox(){
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            try {
                val response = client.getLayer()
                _state.update { it.copy(isRefreshing = false, countryLayer = response) }
        }catch (e: Exception){
            _state.update { it.copy(isRefreshing = false, error = e.message) }}
        }
    }



}


data class UiState(
    var countryLayer : List<LayerItem> = emptyList(),
    val isLoading : Boolean = false,
    val error : String? = null,
    var isRefreshing : Boolean = false,
    val searchText : String = ""
    )