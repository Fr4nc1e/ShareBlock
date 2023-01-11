package com.code.block.core.domain.util

sealed class ActivityType {
    object LikedPost : ActivityType()
    object LikedComment : ActivityType()
    object CommentedOnPost : ActivityType()
    object FollowedYou : ActivityType()
}
