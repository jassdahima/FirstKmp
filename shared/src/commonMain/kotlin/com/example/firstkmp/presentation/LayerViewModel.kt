package com.example.firstkmp.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstkmp.data.LayerItem
import com.example.firstkmp.domain.LayerRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LayerViewModel(private val repository: LayerRepository) : ViewModel(){

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        searchDebounce()
    }


    fun getAllLayers() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null)}

            try {
                val response = repository.getLayers()
                _state.update { it.copy(isLoading = false, countryLayer = response)}
                _state.value = _state.value.copy(isLoading = false, countryLayer = response)
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message)}
            }
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
                val response = repository.getLayersBySearch(countryName)
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
                val response = repository.getLayers()
                _state.update { it.copy(isRefreshing = false, countryLayer = response) }
        }catch (e: Exception){
            _state.update { it.copy(isRefreshing = false, error = e.message) }}
        }
    }

    @OptIn(FlowPreview::class)
    fun searchDebounce(){
        viewModelScope.launch {
            _state.map {
                it.searchText
            }
                .debounce(500)
                .distinctUntilChanged()
                .filter { it.length > 4 }
                .collect {
                    query ->
                    getLayerBySearch(query)
                }
        }
    }



}


data class UiState(
    var countryLayer : List<LayerItem> = emptyList(),
    val isLoading : Boolean = false,
    val error : String? = null,
    val isRefreshing : Boolean = false,
    val searchText : String = ""
    )