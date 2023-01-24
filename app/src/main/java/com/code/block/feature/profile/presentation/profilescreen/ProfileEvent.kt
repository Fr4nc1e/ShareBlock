package com.code.block.feature.profile.presentation.profilescreen

sealed class ProfileEvent {
    data class GetProfile(val userId: String) : ProfileEvent()
    data class LikePost(val postId: String) : ProfileEvent()
}
