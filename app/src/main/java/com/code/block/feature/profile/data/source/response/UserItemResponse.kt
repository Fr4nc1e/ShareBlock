package com.code.block.feature.profile.data.source.response

import com.code.block.feature.profile.domain.model.UserItem

data class UserItemResponse(
    val userId: String,
    val username: String,
    val profileImageUrl: String,
    val bio: String,
    val isFollowing: Boolean
) {
    fun toUserItem(): UserItem {
        return UserItem(
            userId = userId,
            username = username,
            profileImageUrl = profileImageUrl,
            bio = bio,
            isFollowing = isFollowing
        )
    }
}
