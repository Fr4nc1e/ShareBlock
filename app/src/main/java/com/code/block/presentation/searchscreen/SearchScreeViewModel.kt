package com.code.block.presentation.searchscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchScreeViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.EnteredSearchText -> {
                _state.value = _state.value.copy(
                    searchText = event.searchText
                )
            }
            is SearchEvent.ClearSearchText -> {
                _state.value = _state.value.copy(
                    searchText = ""
                )
            }
            is SearchEvent.Search -> {
                validateSearchText(searchText = state.value.searchText)
            }
        }
    }

    private fun validateSearchText(searchText: String) {
        val trimmedSearchText = searchText.trim()
        if (trimmedSearchText.isBlank()) {
            _state.value = _state.value.copy(
                searchError = SearchState.SearchError.FieldEmpty
            )
            return
        }

        _state.value = _state.value.copy(
            searchError = null
        )
    }
}
