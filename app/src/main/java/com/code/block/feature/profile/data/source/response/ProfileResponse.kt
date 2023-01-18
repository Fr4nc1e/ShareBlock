package com.code.block.feature.profile.data.source.response

import com.code.block.core.domain.model.Profile

data class ProfileResponse(
    val userId: String,
    val username: String,
    val bio: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int,
    val profilePictureUrl: String,
    val bannerUrl: String,
    val topHobbyUrls: List<String>,
    val gitHubUrl: String?,
    val qqUrl: String?,
    val weChatUrl: String?,
    val isOwnProfile: Boolean,
    val isFollowing: Boolean
) {
    fun toProfile(): Profile {
        return Profile(
            userId = userId,
            username = username,
            bio = bio,
            followingCount = followingCount,
            followerCount = followerCount,
            postCount = postCount,
            profilePictureUrl = profilePictureUrl,
            bannerUrl = bannerUrl,
            topHobbyUrls = topHobbyUrls,
            gitHubUrl = gitHubUrl,
            qqUrl = qqUrl,
            weChatUrl = weChatUrl,
            isOwnProfile = isOwnProfile,
            isFollowing = isFollowing
        )
    }
}
