package com.code.block.feature.profile.presentation.searchscreen

data class SearchState(
    val searchText: String = "",
    val searchError: SearchError? = null,
    val showKeyboard: Boolean = true
) {
    sealed class SearchError {
        object FieldEmpty : SearchError()
        object NoResult : SearchError()
    }
}
