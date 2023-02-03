package com.code.block.feature.profile.presentation.profilescreen

import com.code.block.core.domain.model.Post

sealed class ProfileEvent {
    data class GetProfile(val userId: String) : ProfileEvent()
    data class OwnPageLikePost(val postId: String) : ProfileEvent()
    data class LikePageLikePost(val postId: String) : ProfileEvent()
    data class DeletePost(val post: Post) : ProfileEvent()
    data class LikeComment(val commentId: String) : ProfileEvent()
    data class FollowMotion(val userId: String) : ProfileEvent()
    object ShowMenu : ProfileEvent()
    object DismissLogoutDialog : ProfileEvent()
    object ShowLogoutDialog : ProfileEvent()
    object Logout : ProfileEvent()
}
