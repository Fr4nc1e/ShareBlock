package com.code.block.feature.post.data.source.response

import com.code.block.core.domain.model.Post
import java.text.SimpleDateFormat
import java.util.*

data class PostDto(
    val id: String,
    val userId: String,
    val username: String,
    val contentUrl: String,
    val profilePictureUrl: String?,
    val timestamp: Long,
    val description: String,
    val likeCount: Int,
    val commentCount: Int,
    val isLiked: Boolean,
    val isOwnPost: Boolean,
) {
    fun toPost(): Post {
        return Post(
            id = id,
            userId = userId,
            username = username,
            contentUrl = contentUrl,
            profilePictureUrl = profilePictureUrl,
            timestamp = SimpleDateFormat(
                "MMM dd, HH:mm",
                Locale.getDefault(),
            ).run {
                format(timestamp)
            },
            description = description,
            likeCount = likeCount,
            commentCount = commentCount,
            isLiked = isLiked,
            isOwnPost = isOwnPost,
        )
    }
}
