package com.code.block.feature.profile.presentation.searchscreen

sealed class SearchEvent {
    data class FollowMotion(val userId: String) : SearchEvent()
    data class EnteredSearchText(val searchText: String) : SearchEvent()
    object ClearSearchText : SearchEvent()
    object Search : SearchEvent()
}
