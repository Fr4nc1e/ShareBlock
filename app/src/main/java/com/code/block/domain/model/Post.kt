package com.code.block.domain.model

data class Post(
    val username: String,
    val imageUrl: Int,
    val profilePictureUrl: String,
    val description: String,
    val likeCount: Int,
    val commentCount: Int
)
