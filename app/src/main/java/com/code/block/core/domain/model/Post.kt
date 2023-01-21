package com.code.block.core.domain.model

data class Post(
    val id: String? = null,
    val userId: String? = null,
    val username: String,
    val contentUrl: String,
    val profilePictureUrl: String?,
    val timestamp: Long,
    val description: String,
    val likeCount: Int,
    val commentCount: Int
)
