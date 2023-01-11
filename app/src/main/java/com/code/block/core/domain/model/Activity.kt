package com.code.block.core.domain.model

import com.code.block.core.domain.util.ActivityType

data class Activity(
    val username: String,
    val profileImageUrl: Int,
    val activityType: ActivityType,
    val formattedTime: String
)
