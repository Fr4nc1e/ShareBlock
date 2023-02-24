package com.code.block.feature.profile.presentation.followinfoscreen

import com.code.block.feature.profile.domain.model.UserItem

data class FollowInfoState(
    val users: List<UserItem> = emptyList(),
    val isLoading: Boolean = false
)
