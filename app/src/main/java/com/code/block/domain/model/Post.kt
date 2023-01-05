package com.code.block.domain.model

data class Post(
    val username: String,
    val imageUrl: Int,
    val profilePictureUrl: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val description: String,
    val likeCount: Int,
    val commentCount: Int
)
