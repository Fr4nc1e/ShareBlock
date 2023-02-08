package com.code.block.core.domain.model

data class User(
    val userId: String,
    val profilePictureUrl: String,
    val username: String,
    val description: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int,
)
