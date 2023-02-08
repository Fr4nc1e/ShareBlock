package com.code.block.core.domain.model

data class Comment(
    val id: String,
    val userId: String,
    val postId: String,
    val username: String,
    val profilePictureUrl: String,
    val formattedTime: String,
    val comment: String,
    val isLiked: Boolean,
    val likeCount: Int,
)
