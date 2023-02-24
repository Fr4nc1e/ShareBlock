package com.code.block.feature.activity.data.source.response

import com.code.block.core.domain.model.Activity
import com.code.block.core.domain.type.ActivityType
import java.text.SimpleDateFormat
import java.util.*

data class ActivityResponse(
    val timestamp: Long,
    val userId: String,
    val parentId: String,
    val type: Int,
    val username: String,
    val profileImageUrl: String,
    val id: String
) {
    fun toActivity(): Activity {
        return Activity(
            userId = userId,
            parentId = parentId,
            username = username,
            profileImageUrl = profileImageUrl,
            activityType = when (type) {
                ActivityType.FollowedUser.type -> ActivityType.FollowedUser
                ActivityType.LikedPost.type -> ActivityType.LikedPost
                ActivityType.LikedComment.type -> ActivityType.LikedComment
                ActivityType.CommentedOnPost.type -> ActivityType.CommentedOnPost
                else -> ActivityType.FollowedUser
            },
            formattedTime = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).run {
                format(timestamp)
            }
        )
    }
}
