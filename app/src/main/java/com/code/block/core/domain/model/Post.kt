package com.code.block.core.domain.model

data class Post(
    val id: String,
    val userId: String,
    val username: String,
    val contentUrl: String,
    val profilePictureUrl: String?,
    val timestamp: String,
    val description: String,
    val likeCount: Int,
    val commentCount: Int,
    val isLiked: Boolean,
    val isOwnPost: Boolean,
)
