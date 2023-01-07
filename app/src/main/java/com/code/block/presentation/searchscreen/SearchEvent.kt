package com.code.block.presentation.searchscreen

sealed class SearchEvent {
    data class EnteredSearchText(val searchText: String) : SearchEvent()
    object ClearSearchText : SearchEvent()
    object Search : SearchEvent()
}
