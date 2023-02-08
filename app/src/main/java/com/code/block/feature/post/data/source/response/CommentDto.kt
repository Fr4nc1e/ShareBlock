package com.code.block.feature.post.data.source.response

import com.code.block.core.domain.model.Comment
import java.text.SimpleDateFormat
import java.util.*

data class CommentDto(
    val id: String,
    val userId: String,
    val postId: String,
    val username: String,
    val profilePictureUrl: String,
    val timestamp: Long,
    val comment: String,
    val isLiked: Boolean,
    val likeCount: Int,
) {
    fun toComment(): Comment {
        return Comment(
            id = id,
            userId = userId,
            postId = postId,
            username = username,
            profilePictureUrl = profilePictureUrl,
            formattedTime = SimpleDateFormat(
                "MMM dd, HH:mm",
                Locale.getDefault(),
            ).run {
                format(timestamp)
            },
            comment = comment,
            isLiked = isLiked,
            likeCount = likeCount,
        )
    }
}
