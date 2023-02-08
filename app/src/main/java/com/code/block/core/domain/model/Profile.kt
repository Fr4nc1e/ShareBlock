package com.code.block.core.domain.model

data class Profile(
    val userId: String,
    val username: String,
    val bio: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int,
    val profilePictureUrl: String,
    val bannerUrl: String?,
    val gitHubUrl: String?,
    val qqUrl: String?,
    val weChatUrl: String?,
    val isOwnProfile: Boolean,
    val isFollowing: Boolean,
)
