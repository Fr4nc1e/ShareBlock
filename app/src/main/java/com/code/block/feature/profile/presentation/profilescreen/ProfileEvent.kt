package com.code.block.feature.profile.presentation.profilescreen

sealed class ProfileEvent {
    data class GetProfile(val userId: String) : ProfileEvent()
    data class OwnPageLikePost(val postId: String) : ProfileEvent()
    data class LikePageLikePost(val postId: String) : ProfileEvent()
    data class LikeComment(val commentId: String) : ProfileEvent()
    data class FollowMotion(val userId: String) : ProfileEvent()
}
