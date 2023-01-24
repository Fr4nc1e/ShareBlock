package com.code.block.feature.post.presentation.personlistscreen

import com.code.block.feature.profile.domain.model.UserItem

data class PersonListState(
    val users: List<UserItem> = emptyList(),
    val isLoading: Boolean = false
)
