package com.code.block.feature.profile.presentation.searchscreen

import com.code.block.feature.profile.domain.model.UserItem

data class SearchState(
    val userItems: List<UserItem> = emptyList(),
    val showKeyboard: Boolean = true,
    val isLoading: Boolean = false
)
