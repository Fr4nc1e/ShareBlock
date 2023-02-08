package com.code.block.feature.profile.domain.model

data class UserItem(
    val userId: String,
    val username: String,
    val profileImageUrl: String,
    val bio: String,
    val isFollowing: Boolean,
)
