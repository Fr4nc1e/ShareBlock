package com.code.block.core.domain.model

import com.code.block.core.domain.type.ActivityType

data class Activity(
    val userId: String,
    val parentId: String,
    val username: String,
    val profileImageUrl: String,
    val activityType: ActivityType,
    val formattedTime: String
)
