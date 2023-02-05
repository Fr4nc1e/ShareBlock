package com.code.block.feature.profile.presentation.followinfoscreen

sealed class FollowInfoEvent {
    data class FollowUser(val userId: String) : FollowInfoEvent()
}
